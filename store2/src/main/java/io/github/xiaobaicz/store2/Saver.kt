package io.github.xiaobaicz.store2

interface Saver {

    /**
     * field key exist?
     * @param table target table
     * @param key field key
     * @return exist?
     */
    fun has(table: String, key: String): Boolean

    /**
     * store field value
     * @param table target table
     * @param key field key
     * @param value field value
     */
    fun store(table: String, key: String, value: String)

    /**
     * restore field value
     * @param table target table
     * @param key field key
     * @return field value
     */
    fun restore(table: String, key: String): String

    /**
     * remove field
     * @param table target table
     * @param key field key
     */
    fun remove(table: String, key: String)

    /**
     * clear table
     * @param table target table
     */
    fun clear(table: String)

}