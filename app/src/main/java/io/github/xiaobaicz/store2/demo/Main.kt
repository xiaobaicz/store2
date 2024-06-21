package io.github.xiaobaicz.store2.demo

import io.github.xiaobaicz.store2.Store
import io.github.xiaobaicz.store2.annotation.AnyDef
import io.github.xiaobaicz.store2.annotation.IntDef
import io.github.xiaobaicz.store2.saver.MMapSaver
import io.github.xiaobaicz.store2.serializer.GsonSerializer

fun main() {
    MMapSaver.dir = "."
    // get table
    val local: Local by localStore
    // check key
    println(localStore.has(Local::version))
    // get
    println(local.debug)
    println(local.version)
    println(local.user)
    // set
    local.version = 110
    // remove key
    localStore.remove(Local::version)
    // clear table
    localStore.clear()
}

val storeFactory = Store.Factory()
    .saver(MMapSaver)
    .serializer(GsonSerializer)

val localStore = storeFactory.get<Local>()

interface Local {

    var debug: Boolean

    @IntDef(666)
    var version: Int

    @AnyDef("{\"name\": \"逗比\"}")
    var user: User

}

data class User(
    val name: String,
)