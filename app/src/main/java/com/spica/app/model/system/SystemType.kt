package com.spica.app.model.system


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SystemType(
  @Json(name = "data")
  val `data`: List<SystemData>,
  @Json(name = "errorCode")
  val errorCode: Int,
  @Json(name = "errorMsg")
  val errorMsg: String
)