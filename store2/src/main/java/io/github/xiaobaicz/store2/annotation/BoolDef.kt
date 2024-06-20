package io.github.xiaobaicz.store2.annotation

@Target(AnnotationTarget.PROPERTY)
annotation class BoolDef(
    val value: Boolean = false
)
