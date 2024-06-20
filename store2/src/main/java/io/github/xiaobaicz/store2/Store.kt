package io.github.xiaobaicz.store2

import io.github.xiaobaicz.store2.annotation.*
import io.github.xiaobaicz.store2.exception.MethodDeclarationClassException
import io.github.xiaobaicz.store2.exception.MethodMatchingException
import io.github.xiaobaicz.store2.saver.MemorySaver
import io.github.xiaobaicz.store2.serializer.SimpleSerializer
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.jvm.javaSetter

class Store<R : Any> private constructor(
    private val table: String,
    private val kClass: KClass<R>,
    private val saver: Saver,
    private val serializer: Serializer
) {

    // Java方法->访问器 映射
    private val accessorMap = HashMap<Method, KProperty.Accessor<*>>().apply {
        kClass.declaredMemberProperties.forEach {
            if (it is KMutableProperty<*>) {
                this[it.javaGetter!!] = it.getter
                this[it.javaSetter!!] = it.setter
            }
        }
    }

    private fun handle(method: Method, args: Array<out Any>): Any? {
        val accessor = accessorMap[method] ?: throw MethodMatchingException(method)
        return if (accessor is KProperty.Getter<*>) {
            getter(accessor.returnType, accessor.property)
        } else {
            setter(accessor.property, args[0])
        }
    }

    // 处理Getter
    private fun <T> getter(returnType: KType, kProperty: KProperty<T>): Any? {
        if (!has(kProperty)) {
            if (returnType.isMarkedNullable) return null
            return getDef(returnType, kProperty)
        }
        // 获取&反序列化
        val data = restore(kProperty)
        val any = serializer.deserialization(returnType, data)
        return any
    }

    // 处理Setter
    private fun <T> setter(kProperty: KProperty<T>, arg: Any?) {
        // 序列化&存储
        if (arg == null) {
            remove(kProperty)
            return
        }
        val data = serializer.serialization(arg)
        store(kProperty, data)
    }

    private inline fun <reified T> List<Annotation>.find(): T {
        return find { it is T } as T
    }

    private fun <T> getDef(type: KType, kProperty: KProperty<T>): Any {
        val annotations = kProperty.annotations
        return when (type.classifier) {
            Byte::class -> annotations.find<ByteDef>().value
            Short::class -> annotations.find<ShortDef>().value
            Int::class -> annotations.find<IntDef>().value
            Long::class -> annotations.find<LongDef>().value
            Float::class -> annotations.find<FloatDef>().value
            Double::class -> annotations.find<DoubleDef>().value
            Boolean::class -> annotations.find<BoolDef>().value
            String::class -> annotations.find<StringDef>().value
            else -> serializer.deserialization(type, annotations.find<AnyDef>().value)
        }
    }

    fun <T> has(kProperty: KProperty<T>): Boolean = saver.has(table, kProperty.name)

    fun <T> store(kProperty: KProperty<T>, value: String) {
        saver.store(table, kProperty.name, value)
    }

    fun <T> restore(kProperty: KProperty<T>): String {
        return saver.restore(table, kProperty.name)
    }

    fun <T> remove(kProperty: KProperty<T>) {
        saver.remove(table, kProperty.name)
    }

    fun clear() {
        saver.clear(table)
    }

    private val proxy by lazy {
        @Suppress("UNCHECKED_CAST")
        Proxy.newProxyInstance(kClass.java.classLoader, arrayOf(kClass.java)) { _, method, args ->
            val argList = args ?: arrayOf()
            when (method.declaringClass.kotlin) {
                kClass -> handle(method, argList)
                Any::class -> method.invoke(this, *argList)
                else -> MethodDeclarationClassException(kClass, method)
            }
        } as R
    }

    operator fun getValue(r: Any?, property: KProperty<*>): R = proxy

    companion object {
        private val storeCache = HashMap<String, Store<*>>()
    }

    class Factory {
        private var saver: Saver = MemorySaver

        private var serializer: Serializer = SimpleSerializer

        private fun <T : Any> newAndCache(table: String, kClass: KClass<T>): Store<T> {
            return Store(table, kClass, saver, serializer).apply {
                storeCache[table] = this
            }
        }

        private fun <T : Any> newToken(kClass: KClass<T>, saver: Saver, serializer: Serializer): String {
            return "${kClass.qualifiedName}:${saver::class.qualifiedName}:${serializer::class.qualifiedName}"
        }

        fun <T : Any> get(kClass: KClass<T>): Store<T> {
            val token = newToken(kClass, saver, serializer)
            val table = sha1(token)
            @Suppress("UNCHECKED_CAST")
            return storeCache[table] as Store<T>? ?: newAndCache(table, kClass)
        }

        inline fun <reified T : Any> get(): Store<T> = get(T::class)

        fun saver(saver: Saver): Factory {
            this.saver = saver
            return this
        }

        fun serializer(serializer: Serializer): Factory {
            this.serializer = serializer
            return this
        }
    }
}
