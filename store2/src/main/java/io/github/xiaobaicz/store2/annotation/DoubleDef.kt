package io.github.xiaobaicz.store2.annotation

@Target(AnnotationTarget.PROPERTY)
annotation class DoubleDef(
    val value: Double = 0.0
)
