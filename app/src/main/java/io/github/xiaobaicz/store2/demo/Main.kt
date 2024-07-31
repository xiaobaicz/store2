package io.github.xiaobaicz.store2.demo

import io.github.xiaobaicz.store2.Saver
import io.github.xiaobaicz.store2.Serializer
import io.github.xiaobaicz.store2.Store
import io.github.xiaobaicz.store2.demo.store.Local
import io.github.xiaobaicz.store2.saver.MMapSaver
import io.github.xiaobaicz.store2.saver.MemorySaver
import io.github.xiaobaicz.store2.serializer.JsonSerializer

fun main() {
    MMapSaver.dir = "."
    // check key
    println(Local.has(Local::version))
    // get
    println(Local.debug)
    println(Local.version)
    println(Local.user)
    // set
    Local.version = 110
    // remove key
    Local.remove(Local::version)
    // clear table
    Local.clear()
}
