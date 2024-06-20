package io.github.xiaobaicz.store2.demo

import io.github.xiaobaicz.store2.Store
import io.github.xiaobaicz.store2.saver.MMapSaver
import io.github.xiaobaicz.store2.serializer.GsonSerializer

fun main() {
    MMapSaver.dir = "../"
    val local: Local by localStore
    println(localStore.has(Local::version))
    local.version = 100
    println(local.version)
}

val storeFactory = Store.Factory()
    .saver(MMapSaver)
    .serializer(GsonSerializer)

val localStore = storeFactory.get<Local>()

interface Local {
    var version: Int
}