package io.github.xiaobaicz.store2.serializer

import com.google.gson.Gson
import io.github.xiaobaicz.store2.Serializer
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType

object JsonSerializer : Serializer {
    private val gson = Gson()

    override fun serialization(any: Any): String {
        return gson.toJson(any)
    }

    override fun deserialization(returnType: KType, value: String): Any {
        return gson.fromJson(value, returnType.javaType)
    }
}