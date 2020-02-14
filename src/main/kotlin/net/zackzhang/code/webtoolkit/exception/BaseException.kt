package net.zackzhang.code.webtoolkit.exception

open class BaseException(val code: Int, message: String): RuntimeException(message)