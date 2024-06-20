package io.github.xiaobaicz.store2.annotation

@Target(AnnotationTarget.PROPERTY)
annotation class LongDef(
    val value: Long = 0L
)
