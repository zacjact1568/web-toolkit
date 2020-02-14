package net.zackzhang.code.webtoolkit.exception

open class BaseException(val code: Int, msg: String): RuntimeException(msg)