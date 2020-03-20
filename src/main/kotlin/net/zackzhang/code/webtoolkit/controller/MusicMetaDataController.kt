package net.zackzhang.code.webtoolkit.controller

import net.zackzhang.code.webtoolkit.exception.WrongPasswordException
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

// 相当于 @ResponseBody + @Controller，返回 JSON
@RestController
class MusicMetaDataController {

    @Value("\${password}")
    private lateinit var password: String

    @PostMapping("/music-meta-data")
    fun parse(
            @RequestParam("title") title: String,
            @RequestParam("artist") artist: String,
            @RequestParam("album") album: String,
            @RequestParam("album-artist") albumArtist: String,
            @RequestParam("password") password: String
    ): Map<String, Any> {
        // 密码
        if (password != this.password) {
            throw WrongPasswordException()
        }
        // 处理 feat
        var newTitle = title
        var newArtist = artist
        val start = title.indexOf("(feat.")
        if (start > -1) {
            // 有 feat
            val end = title.indexOf(')', start)
            newArtist = "$artist ${title.substring(start + 1 until end)}"
            newTitle = title.removeRange(start - 1 .. end)
        }
        // 匹配不是字母或数字的子串，替换为 -
        val cover = "$albumArtist $album".toLowerCase().replace(Regex("\\W+"), "-").trimEnd('-')
        return mapOf(
                "code" to 200,
                "message" to "OK",
                "title" to newTitle,
                "artist" to newArtist,
                "cover" to cover
        )
    }
}