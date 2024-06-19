package io.github.xiaobaicz.store2

import java.security.MessageDigest

fun hash(alg: String, msg: String): String {
    val md = MessageDigest.getInstance(alg)
    val byte = md.digest(msg.toByteArray())
    return byte.joinToString("") { String.format("%02x", it) }
}

fun sha1(msg: String): String = hash("SHA-1", msg)