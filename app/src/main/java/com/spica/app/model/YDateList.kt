package com.spica.app.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YDateList(
    @Json(name = "data")
    val `data`: List<YData>,
    @Json(name = "res")
    val res: Int
)