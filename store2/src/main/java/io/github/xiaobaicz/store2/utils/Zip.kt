package io.github.xiaobaicz.store2.utils

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

fun zip(src: String): ByteArray {
    ByteArrayOutputStream().use { output ->
        GZIPOutputStream(output).use { zip ->
            zip.write(src.toByteArray())
        }
        return output.toByteArray()
    }
}

fun unzip(src: ByteArray): String {
    ByteArrayInputStream(src).use { input ->
        GZIPInputStream(input).use { unzip ->
            return String(unzip.readBytes())
        }
    }
}