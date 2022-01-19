package cn.tagux.calendar.model.date

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YContent(
    @Json(name = "audio")
    val audio: String,
    @Json(name = "bookmarked")
    val bookmarked: Boolean,
    @Json(name = "bookmarked_count")
    val bookmarkedCount: Int,
    @Json(name = "comment_count")
    val commentCount: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_article")
    val isArticle: Int,
    @Json(name = "like_count")
    val likeCount: Int,
    @Json(name = "liked")
    val liked: Boolean,
    @Json(name = "note_sim")
    val noteSim: String,
    @Json(name = "note_tra")
    val noteTra: String,
    @Json(name = "person_sim")
    val personSim: String,
    @Json(name = "person_tra")
    val personTra: String,
    @Json(name = "picture")
    val picture: String,
    @Json(name = "share_icon")
    val shareIcon: String,
    @Json(name = "share_url")
    val shareUrl: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "title_tra")
    val titleTra: String,
    @Json(name = "type")
    val type: Int,
    @Json(name = "video")
    val video: String,
    @Json(name = "words_sim")
    val wordsSim: String,
    @Json(name = "words_tra")
    val wordsTra: String
)
