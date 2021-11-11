package com.spica.app.model.banner


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class BannerData(
  @Json(name = "desc")
  var desc: String,
  @PrimaryKey
  @Json(name = "id")
  var id: Int,
  @Json(name = "imagePath")
  var imagePath: String,
  @Json(name = "isVisible")
  var isVisible: Int,
  @Json(name = "order")
  var order: Int,
  @Json(name = "title")
  var title: String,
  @Json(name = "type")
  var type: Int,
  @Json(name = "url")
  var url: String
)