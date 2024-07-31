package io.github.xiaobaicz.store2.demo.store

import io.github.xiaobaicz.store2.Store
import io.github.xiaobaicz.store2.annotation.AnyDef
import io.github.xiaobaicz.store2.annotation.IntDef
import io.github.xiaobaicz.store2.demo.entity.User
import io.github.xiaobaicz.store2.saver.MMapSaver
import io.github.xiaobaicz.store2.store

private val store = store<Local>(MMapSaver)
private val local by store

interface Local {

    companion object : Store<Local> by store, Local by local

    var debug: Boolean

    @IntDef(666)
    var version: Int

    @AnyDef("{\"name\": \"逗比\"}")
    var user: User

}