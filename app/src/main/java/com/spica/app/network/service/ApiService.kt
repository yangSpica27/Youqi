package com.spica.app.network.service

import com.skydoves.sandwich.ApiResponse
import com.spica.app.model.comment.CommentItem
import com.spica.app.model.date.YDateList
import retrofit2.http.GET
import retrofit2.http.Query


@Suppress("unused")
interface ApiService {


  // 获取首页信息列表
  @GET("datelist")
  suspend fun getDateList(): ApiResponse<YDateList>


  // 获取评论
  @GET("comment/list")
  suspend fun getCommentList(
    @Query("content_id")
    contentId: Int,
    @Query("page")
    page: Int,
    @Query("type")
    type: Int?
  ): ApiResponse<List<CommentItem>>

}