package com.spica.app.model.user


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
@Entity
data class UserData(


  @PrimaryKey(autoGenerate = false)
  var did: Int? = 1,

  @Json(name = "admin")
  var admin: Boolean = true,

  @Ignore
  @Json(name = "chapterTops")
  var chapterTops: List<String> = listOf(),

  @Ignore
  @Json(name = "collectIds")
  var collectIds: List<Int> = listOf(),

  @Json(name = "email")
  var email: String = "",
  @Json(name = "icon")
  var icon: String = "",
  @Json(name = "id")
  var id: Int = 1,
  @Json(name = "nickname")
  var nickname: String = "",
  @Json(name = "password")
  var password: String = "",
  @Json(name = "publicName")
  var publicName: String = "",
  @Json(name = "token")
  var token: String = "",
  @Json(name = "type")
  var type: Int = -1,
  @Json(name = "username")
  var username: String = ""
)