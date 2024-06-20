package io.github.xiaobaicz.store2.serializer

import io.github.xiaobaicz.store2.Serializer
import kotlin.reflect.KType

object SimpleSerializer : Serializer {

    override fun serialization(any: Any): String {
        return any.toString()
    }

    override fun deserialization(returnType: KType, value: String): Any {
        return when (returnType.classifier) {
            Byte::class -> convert(0) { value.toByte() }
            Short::class -> convert(0) { value.toShort() }
            Int::class -> convert(0) { value.toInt() }
            Long::class -> convert(0L) { value.toLong() }
            Float::class -> convert(0f) { value.toFloat() }
            Double::class -> convert(0.0) { value.toDouble() }
            Boolean::class -> convert(false) { value.toBoolean() }
            String::class -> value
            else -> throw IllegalArgumentException()
        }
    }

    private fun <T : Any> convert(default: T, func: () -> T): T = try {
        func()
    } catch (_: Throwable) {
        default
    }

}