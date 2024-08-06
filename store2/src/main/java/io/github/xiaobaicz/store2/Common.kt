package io.github.xiaobaicz.store2

import io.github.xiaobaicz.store2.saver.MemorySaver
import io.github.xiaobaicz.store2.serializer.JsonSerializer

inline fun <reified R : Any> store(
    saver: Saver = MemorySaver,
    serializer: Serializer = JsonSerializer,
    block: Store.Factory.() -> Unit = {}
): Store<R> = store(factory(saver, serializer), block)

inline fun <reified R : Any> store(
    factory: Store.Factory,
    block: Store.Factory.() -> Unit = {}
): Store<R> {
    factory.block()
    return factory.get()
}

fun factory(
    saver: Saver = MemorySaver,
    serializer: Serializer = JsonSerializer
): Store.Factory = Store.Factory(saver, serializer)