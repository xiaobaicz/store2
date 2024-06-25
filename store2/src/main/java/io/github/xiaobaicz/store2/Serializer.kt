package io.github.xiaobaicz.store2

import kotlin.reflect.KType

interface Serializer {

    /**
     * serialization
     * @param any target object
     * @return serialization string
     */
    fun serialization(any: Any): String

    /**
     * deserialization
     * @param returnType target type
     * @param value serialization string
     * @return target object
     */
    fun deserialization(returnType: KType, value: String): Any

}