package io.github.xiaobaicz.store2.saver

import com.tencent.mmkv.MMKV
import java.util.ServiceLoader

interface MMKVFactory {
    fun create(table: String): MMKV
}

private class DefaultMMKVFactory : MMKVFactory {
    override fun create(table: String): MMKV {
        return MMKV.mmkvWithID(table, MMKV.SINGLE_PROCESS_MODE)
    }
}

internal val mmkvFactory: MMKVFactory by lazy {
    val clazz = MMKVFactory::class.java
    ServiceLoader.load(clazz, clazz.classLoader).toList().firstOrNull() ?: DefaultMMKVFactory()
}