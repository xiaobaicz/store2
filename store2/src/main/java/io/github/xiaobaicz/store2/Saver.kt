package io.github.xiaobaicz.store2

interface Saver {
    fun has(table: String, key: String): Boolean

    fun store(table: String, key: String, value: String)

    fun restore(table: String, key: String): String

    fun remove(table: String, key: String)

    fun clear(table: String)

    companion object : Saver {
        private val tables = HashMap<String, HashMap<String, String>>()

        private fun table(table: String): HashMap<String, String> {
            return tables[table] ?: HashMap<String, String>().apply {
                tables[table] = this
            }
        }

        override fun has(table: String, key: String): Boolean {
            return table(table).containsKey(key)
        }

        override fun store(table: String, key: String, value: String) {
            table(table)[key] = value
        }

        override fun restore(table: String, key: String): String {
            return table(table)[key] ?: ""
        }

        override fun remove(table: String, key: String) {
            table(table).remove(key)
        }

        override fun clear(table: String) {
            table(table).clear()
        }
    }
}