package io.github.xiaobaicz.store2.saver

import io.github.xiaobaicz.store2.Saver

object MMapSaver : Saver {
    var dir: String = MMap.tmpDir

    private val tables = HashMap<String, MMap>()

    private fun table(table: String): MMap {
        return tables[table] ?: MMap(table, dir).apply {
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