package net.zackzhang.code.webtoolkit.exception

// Spring 对于 RuntimeException 才会进行事务回滚
// 所以一般自定义异常都继承该类
open class BaseException(val code: Int, message: String): RuntimeException(message)