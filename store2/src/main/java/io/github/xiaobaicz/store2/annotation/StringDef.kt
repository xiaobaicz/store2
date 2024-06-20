package io.github.xiaobaicz.store2.annotation

@Target(AnnotationTarget.PROPERTY)
annotation class StringDef(
    val value: String = ""
)
