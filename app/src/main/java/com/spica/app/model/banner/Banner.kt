package com.spica.app.model.banner


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Banner(
  @Json(name = "data")
    val `data`: List<BannerData>,
  @Json(name = "errorCode")
    val errorCode: Int,
  @Json(name = "errorMsg")
    val errorMsg: String
)