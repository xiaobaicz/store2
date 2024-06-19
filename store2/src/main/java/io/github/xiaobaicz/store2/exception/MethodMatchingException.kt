package io.github.xiaobaicz.store2.exception

import java.lang.reflect.Method

class MethodMatchingException(
    method: Method
) : RuntimeException(
    "$method"
)