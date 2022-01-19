package cn.tagux.calendar.model.comment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentItem(
    @Json(name = "content")
    val content: String,
    @Json(name = "content_id")
    val contentId: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_liked_by_me")
    val isLikedByMe: Boolean,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "to_comment")
    val toComment: Any?,
    @Json(name = "user")
    val user: User
)
