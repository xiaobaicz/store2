package io.github.xiaobaicz.store2

import kotlin.reflect.KType

interface Serializer {

    fun serialization(any: Any): String

    fun deserialization(returnType: KType, value: String): Any

}