package io.github.xiaobaicz.store2.saver

import com.tencent.mmkv.MMKV
import io.github.xiaobaicz.store2.Saver

object MMKVSaver : Saver {

    private val tables = HashMap<String, MMKV>()

    private fun table(table: String): MMKV {
        return tables[table] ?: mmkvFactory.create(table).apply {
            tables[table] = this
        }
    }

    override fun has(table: String, key: String): Boolean {
        return table(table).containsKey(key)
    }

    override fun store(table: String, key: String, value: String) {
        table(table).putString(key, value)
    }

    override fun restore(table: String, key: String): String {
        return table(table).getString(key, "") ?: ""
    }

    override fun remove(table: String, key: String) {
        table(table).remove(key)
    }

    override fun clear(table: String) {
        table(table).clearAll()
    }

}