package com.spica.app.network


import com.skydoves.sandwich.ApiResponse
import com.spica.app.model.comment.CommentItem
import com.spica.app.model.date.YDateList
import com.spica.app.network.service.ApiService
import javax.inject.Inject


/**
 * 网络请求的Client
 */
class AppClient
@Inject constructor(
  private val apiService: ApiService
) {


  suspend fun getDateList(): ApiResponse<YDateList> =
    apiService.getDateList()


  suspend fun getCommentList(
    contentId: Int,
    page: Int,
    type: Int? = null
  ): ApiResponse<List<CommentItem>> =
    apiService.getCommentList(contentId, page, type)


}