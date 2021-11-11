package com.spica.app.model.navigation


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Navigation(
    @Json(name = "data")
    val `data`: List<NavigationData>,
    @Json(name = "errorCode")
    val errorCode: Int,
    @Json(name = "errorMsg")
    val errorMsg: String
)