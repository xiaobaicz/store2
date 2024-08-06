package io.github.xiaobaicz.store2.saver.mmap

import io.github.xiaobaicz.store2.Serializer
import io.github.xiaobaicz.store2.Store
import io.github.xiaobaicz.store2.factory
import io.github.xiaobaicz.store2.saver.MMapSaver
import io.github.xiaobaicz.store2.serializer.JsonSerializer
import io.github.xiaobaicz.store2.store

inline fun <reified R : Any> store(
    serializer: Serializer = JsonSerializer,
    saver: MMapSaver = MMapSaver,
    block: Store.Factory.() -> Unit = {}
): Store<R> = store(factory(saver, serializer), block)
