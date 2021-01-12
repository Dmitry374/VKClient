package com.dima.vkclient.utils

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

object Reflection {
    fun <T> newProxy(interfaceType: Class<T>, handler: InvocationHandler): T {
        require(interfaceType.isInterface) { "$interfaceType is not an interface" }
        return Proxy.newProxyInstance(
            interfaceType.classLoader, arrayOf<Class<*>>(interfaceType), handler
        ).let { interfaceType.cast(it)!! }
    }
}