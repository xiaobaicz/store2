package io.github.xiaobaicz.store2.demo.store

import io.github.xiaobaicz.store2.Store
import io.github.xiaobaicz.store2.saver.MMapSaver

val factory = Store.Factory(MMapSaver)