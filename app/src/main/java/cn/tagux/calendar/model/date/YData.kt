package cn.tagux.calendar.model.date

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

const val NORMAL = 0
const val ARTICLE = 1
const val AUDIO = 2
const val PIC = 3
@JsonClass(generateAdapter = true)
data class YData(
    @Json(name = "content")
    val content: YContent,
    @Json(name = "date")
    val date: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "lunar")
    val lunar: String,
    @Json(name = "wuhou")
    val wuhou: String,
    @Json(name = "wuhou_picture")
    val wuhouPicture: String,
    @Json(name = "wuhou_picture_tra")
    val wuhouPictureTra: String,
    @Json(name = "wuhou_tra")
    val wuhouTra: String,
) : MultiItemEntity {
    override val itemType: Int
        get() {
            if (content.isArticle == 1) {
                return ARTICLE
            }
            if (content.audio.isNotEmpty()) {
                return AUDIO
            }

            if (content.picture.isNotEmpty()) {
                return PIC
            }

            return NORMAL
        }
}
