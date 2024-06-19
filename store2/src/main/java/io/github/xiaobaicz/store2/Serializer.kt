package io.github.xiaobaicz.store2

import com.google.gson.Gson
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

interface Serializer {
    fun serialization(any: Any): String

    fun deserialization(returnType: KType, value: String): Any

    companion object : Serializer {
        private val gson = Gson()

        override fun serialization(any: Any): String {
            return gson.toJson(any)
        }

        override fun deserialization(returnType: KType, value: String): Any {
            return gson.fromJson(value, returnType.javaType)
        }
    }
}