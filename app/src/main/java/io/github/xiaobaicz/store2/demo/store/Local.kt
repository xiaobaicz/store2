package io.github.xiaobaicz.store2.demo.store

import io.github.xiaobaicz.store2.annotation.AnyDef
import io.github.xiaobaicz.store2.annotation.IntDef
import io.github.xiaobaicz.store2.demo.entity.User

interface Local {

    var debug: Boolean

    @IntDef(666)
    var version: Int

    @AnyDef("{\"name\": \"逗比\"}")
    var user: User

}