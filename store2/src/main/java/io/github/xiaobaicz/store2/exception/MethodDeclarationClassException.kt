package io.github.xiaobaicz.store2.exception

import java.lang.reflect.Method
import kotlin.reflect.KClass

internal class MethodDeclarationClassException(
    kClass: KClass<*>,
    method: Method
) : RuntimeException(
    "$method declaration class not $kClass / ${Any::class}"
)