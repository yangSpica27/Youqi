package com.spica.app.model.user


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "data")
    val userData: UserData?,
    @Json(name = "errorCode")
    val errorCode: Int,
    @Json(name = "errorMsg")
    val errorMsg: String
)