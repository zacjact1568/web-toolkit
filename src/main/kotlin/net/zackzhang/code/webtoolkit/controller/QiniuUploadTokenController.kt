package net.zackzhang.code.webtoolkit.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.qiniu.util.Auth
import com.qiniu.util.StringMap
import net.zackzhang.code.webtoolkit.exception.NoSuchFolderException
import net.zackzhang.code.webtoolkit.exception.WrongPasswordException
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class QiniuUploadTokenController {

    @Value("\${password}")
    private lateinit var password: String

    @Value("\${upload-token.access-key}")
    private lateinit var accessKey: String

    @Value("\${upload-token.secret-key}")
    private lateinit var secretKey: String

    @Value("\${upload-token.bucket}")
    private lateinit var bucket: String

    @PostMapping("/qiniu-upload-token")
    fun generate(
            @RequestParam("folder") folder: String,
            @RequestParam("file") file: String,
            @RequestParam("password") password: String
    ): Map<String, Any> {
        // 密码
        if (password != this.password) {
            throw WrongPasswordException()
        }
        // 文件夹限制
        if (folder != "post" && folder != "music") {
            throw NoSuchFolderException(folder)
        }
        val auth = Auth.create(accessKey, secretKey)
        val key = "$folder/$file"
        val putPolicy = StringMap()
        putPolicy.put("returnBody", ObjectMapper().writeValueAsString(mapOf(
                "code" to 200,
                "message" to "OK",
                "key" to "$(key)",
                "hash" to "$(etag)"
        )))
        // expire（有效时间）默认 3600 秒
        // 如果未指定 key，putPolicy 中的 scope 为 bucket，只能新增
        // 如果指定了 key，putPolicy 中的 scope 为 bucket:key，可覆盖同名文件
        val token = auth.uploadToken(bucket, key, 3600, putPolicy)
        return mapOf(
                "code" to 200,
                "message" to "OK",
                "key" to key,
                "token" to token
        )
    }
}