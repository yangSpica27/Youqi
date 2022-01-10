package com.spica.app.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YData(
    @Json(name = "content")
    val YContent: YContent,
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
    val wuhouTra: String
)