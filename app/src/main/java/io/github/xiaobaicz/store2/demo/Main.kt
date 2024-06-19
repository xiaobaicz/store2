package io.github.xiaobaicz.store2.demo

import io.github.xiaobaicz.store2.Saver
import io.github.xiaobaicz.store2.Serializer
import io.github.xiaobaicz.store2.Store

val store = Store.Builder()
    .saver(Saver)
    .serializer(Serializer)
    .build<Local>()

fun main() {
    val local: Local by store
    println(store.has(Local::version))
    local.version = 100
    println(local.version)
}

interface Local {
    var version: Int
}