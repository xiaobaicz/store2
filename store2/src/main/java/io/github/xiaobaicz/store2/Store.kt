package io.github.xiaobaicz.store2

import io.github.xiaobaicz.store2.saver.MemorySaver
import io.github.xiaobaicz.store2.serializer.JsonSerializer
import io.github.xiaobaicz.store2.utils.sha1
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

interface Store<R : Any> {

    /**
     * field key exist?
     * @param kProperty table property
     * @return exist?
     */
    fun <T> has(kProperty: KProperty<T>): Boolean

    /**
     * remove field
     * @param kProperty table property
     */
    fun <T> remove(kProperty: KProperty<T>)

    /**
     * clear table
     */
    fun clear()

    /**
     * get field value
     * @param kProperty table property
     */
    operator fun <T> get(kProperty: KProperty<T>): T?

    /**
     * set field value
     * @param kProperty table property
     * @param value field value
     */
    operator fun <T> set(kProperty: KProperty<T>, value: T)

    /**
     * get table instance
     */
    operator fun getValue(r: Any?, property: KProperty<*>): R

    class Factory(
        private val saver: Saver = MemorySaver,
        private val serializer: Serializer = JsonSerializer,
    ) {
        private companion object {
            val storeCache = HashMap<String, Store<*>>()
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
            return storeCache[table] as Store<T>? ?: newStoreAndCache(table, kClass)
        }

        inline fun <reified T : Any> get(): Store<T> = get(T::class)
    }

}