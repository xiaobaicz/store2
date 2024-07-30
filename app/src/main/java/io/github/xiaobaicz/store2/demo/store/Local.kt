package io.github.xiaobaicz.store2.demo.store

import io.github.xiaobaicz.store2.Store
import io.github.xiaobaicz.store2.annotation.AnyDef
import io.github.xiaobaicz.store2.annotation.IntDef
import io.github.xiaobaicz.store2.demo.entity.User

private val localStore = factory.get<Local>()

interface Local {

    companion object : Store<Local> by localStore, Local by localStore.proxy

    var debug: Boolean

    @IntDef(666)
    var version: Int

    @AnyDef("{\"name\": \"逗比\"}")
    var user: User

}