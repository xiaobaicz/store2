package io.github.xiaobaicz.store2.annotation

@Target(AnnotationTarget.PROPERTY)
annotation class FloatDef(
    val value: Float = 0f
)
