package com.spica.app.model.chapters


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Chapters(
    @Json(name = "data")
    val `data`: List<ChapterData>,
    @Json(name = "errorCode")
    val errorCode: Int,
    @Json(name = "errorMsg")
    val errorMsg: String
)