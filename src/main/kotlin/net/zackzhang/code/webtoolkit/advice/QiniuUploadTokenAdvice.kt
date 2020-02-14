package net.zackzhang.code.webtoolkit.advice

import net.zackzhang.code.webtoolkit.exception.NoSuchFolderException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@RestControllerAdvice
class QiniuUploadTokenAdvice {

    @ExceptionHandler(NoSuchFolderException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun noSuchDirectoryHandler(e: NoSuchFolderException) = mapOf(
            "code" to e.code,
            "message" to e.message
    )
}