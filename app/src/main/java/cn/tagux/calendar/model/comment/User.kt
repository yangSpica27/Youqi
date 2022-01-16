package cn.tagux.calendar.model.comment


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "certified")
    val certified: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "nickname")
    val nickname: String,
    @Json(name = "portrait")
    val portrait: String
)