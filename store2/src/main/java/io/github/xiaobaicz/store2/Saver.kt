package io.github.xiaobaicz.store2

interface Saver {

    fun has(table: String, key: String): Boolean

    fun store(table: String, key: String, value: String)

    fun restore(table: String, key: String): String

    fun remove(table: String, key: String)

    fun clear(table: String)

}