package cn.tagux.calendar.network


import cn.tagux.calendar.model.comment.CommentItem
import cn.tagux.calendar.model.date.YDateList
import cn.tagux.calendar.network.service.ApiService
import com.skydoves.sandwich.ApiResponse
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


    /**
     * QQ 登陆
     */
    suspend fun login(openid: String, access_token: String): ApiResponse<Any> =
        apiService.login(account = openid, accessToken = access_token)

}