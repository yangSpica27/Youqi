package com.spica.app.model.navigation


import com.spica.app.model.article.ArticleItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NavigationData(
  @Json(name = "articles")
  val articles: List<ArticleItem>,
  @Json(name = "cid")
  val cid: Int,
  @Json(name = "name")
  val name: String
)