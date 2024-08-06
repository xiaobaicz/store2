package io.github.xiaobaicz.store2

import io.github.xiaobaicz.store2.annotation.AnyDef
import io.github.xiaobaicz.store2.annotation.BoolDef
import io.github.xiaobaicz.store2.annotation.ByteDef
import io.github.xiaobaicz.store2.annotation.DoubleDef
import io.github.xiaobaicz.store2.annotation.FloatDef
import io.github.xiaobaicz.store2.annotation.IntDef
import io.github.xiaobaicz.store2.annotation.LongDef
import io.github.xiaobaicz.store2.annotation.ShortDef
import io.github.xiaobaicz.store2.annotation.StringDef
import io.github.xiaobaicz.store2.exception.MethodDeclarationClassException
import io.github.xiaobaicz.store2.exception.MethodMatchingException
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.jvm.javaSetter
import kotlin.collections.find as listFind

internal class StoreImpl<R : Any>(
    private val table: String,
    private val kClass: KClass<R>,
    private val saver: Saver,
    private val serializer: Serializer
) : Store<R> {

    private companion object {
        val byteDef = ByteDef()
        val shortDef = ShortDef()
        val intDef = IntDef()
        val longDef = LongDef()
        val floatDef = FloatDef()
        val doubleDef = DoubleDef()
        val boolDef = BoolDef()
        val stringDef = StringDef()
        val anyDef = AnyDef("")
    }

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
            get(accessor.property)
        } else {
            set(accessor.property, args[0])
        }
    }

    private inline fun <reified T : Annotation> List<Annotation>.find(def: T): T {
        return listFind { it is T } as T? ?: def
    }

    private fun <T> getDef(type: KType, kProperty: KProperty<T>): Any {
        val annotations = kProperty.annotations
        return when (type.classifier) {
            Byte::class -> annotations.find(byteDef).value
            Short::class -> annotations.find(shortDef).value
            Int::class -> annotations.find(intDef).value
            Long::class -> annotations.find(longDef).value
            Float::class -> annotations.find(floatDef).value
            Double::class -> annotations.find(doubleDef).value
            Boolean::class -> annotations.find(boolDef).value
            String::class -> annotations.find(stringDef).value
            else -> serializer.deserialization(type, annotations.find(anyDef).value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override val proxy = Proxy.newProxyInstance(kClass.java.classLoader, arrayOf(kClass.java)) { _, method, args ->
        val argList = args ?: arrayOf()
        when (method.declaringClass.kotlin) {
            kClass -> handle(method, argList)
            Any::class -> method.invoke(this, *argList)
            else -> MethodDeclarationClassException(kClass, method)
        }
    } as R

    override fun <T> has(kProperty: KProperty<T>): Boolean = saver.has(table, kProperty.name)

    override fun <T> remove(kProperty: KProperty<T>) {
        saver.remove(table, kProperty.name)
    }

    override fun clear() {
        saver.clear(table)
    }

    @Suppress("UNCHECKED_CAST")
    override operator fun <T> get(kProperty: KProperty<T>): T? {
        val returnType = kProperty.returnType
        if (!has(kProperty)) {
            if (returnType.isMarkedNullable) return null
            return getDef(returnType, kProperty) as T
        }
        // 获取&反序列化
        val data = saver.restore(table, kProperty.name)
        val any = serializer.deserialization(returnType, data)
        return any as T
    }

    override operator fun <T> set(kProperty: KProperty<T>, value: T) {
        // 序列化&存储
        if (value == null) {
            remove(kProperty)
            return
        }
        val data = serializer.serialization(value)
        saver.store(table, kProperty.name, data)
    }
}
