package io.github.xiaobaicz.store2

import io.github.xiaobaicz.store2.saver.MemorySaver
import io.github.xiaobaicz.store2.serializer.JsonSerializer
import io.github.xiaobaicz.store2.utils.sha1
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

interface Store<R : Any> {

    fun <T> has(kProperty: KProperty<T>): Boolean

    fun <T> remove(kProperty: KProperty<T>)

    fun clear()

    operator fun <T> get(kProperty: KProperty<T>): T?

    operator fun <T> set(kProperty: KProperty<T>, value: T)

    operator fun getValue(r: Any?, property: KProperty<*>): R

    class Factory(
        private val saver: Saver = MemorySaver,
        private val serializer: Serializer = JsonSerializer,
    ) {
        private companion object {
            val storeCache = HashMap<String, StoreImpl<*>>()
        }

        private fun <T : Any> newStoreAndCache(table: String, kClass: KClass<T>): Store<T> {
            return StoreImpl(table, kClass, saver, serializer).apply {
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
            return storeCache[table] as StoreImpl<T>? ?: newStoreAndCache(table, kClass)
        }

        inline fun <reified T : Any> get(): Store<T> = get(T::class)
    }

}