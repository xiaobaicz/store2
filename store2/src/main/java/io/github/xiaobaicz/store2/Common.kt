package io.github.xiaobaicz.store2

import io.github.xiaobaicz.store2.saver.MemorySaver
import io.github.xiaobaicz.store2.serializer.JsonSerializer

inline fun <reified R : Any> store(
    saver: Saver = MemorySaver,
    serializer: Serializer = JsonSerializer,
    block: Store.Factory.() -> Unit = {}
): Store<R> {
    val factory = Store.Factory(saver, serializer)
    factory.block()
    return factory.get()
}