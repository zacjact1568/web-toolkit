package net.zackzhang.code.webtoolkit.advice

import net.zackzhang.code.webtoolkit.exception.WrongPasswordException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@RestControllerAdvice
class MusicMetaDataAdvice {

    // 自定义的异常
    @ExceptionHandler(WrongPasswordException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleWrongPasswordException(e: WrongPasswordException) = mapOf(
            "code" to e.code,
            "message" to e.message!!
    )

    // 拦截默认的缺少参数异常
    @ExceptionHandler(MissingServletRequestParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException) = mapOf(
            "code" to 400,
            "message" to e.message
    )
}