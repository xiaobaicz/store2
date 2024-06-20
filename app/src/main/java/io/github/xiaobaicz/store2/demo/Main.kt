package io.github.xiaobaicz.store2.demo

import io.github.xiaobaicz.store2.Store
import io.github.xiaobaicz.store2.annotation.AnyDef
import io.github.xiaobaicz.store2.annotation.IntDef
import io.github.xiaobaicz.store2.saver.MMapSaver
import io.github.xiaobaicz.store2.serializer.GsonSerializer

fun main() {
    MMapSaver.dir = "."
    val local: Local by localStore
    println(localStore.has(Local::version))
    println(local.version)
    println(local.user)
    local.version = 110
}

val storeFactory = Store.Factory()
    .saver(MMapSaver)
    .serializer(GsonSerializer)

val localStore = storeFactory.get<Local>()

interface Local {

    @IntDef(666)
    var version: Int

    @AnyDef("{\"name\": \"逗比\"}")
    var user: User

}

data class User(
    val name: String,
)