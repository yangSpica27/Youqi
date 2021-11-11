package com.spica.app.model.article


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    @Json(name = "data")
    val articleData: ArticleData,
    @Json(name = "errorCode")
    val errorCode: Int,
    @Json(name = "errorMsg")
    val errorMsg: String
)