package cn.tagux.calendar.network.service

import cn.tagux.calendar.model.comment.CommentItem
import cn.tagux.calendar.model.date.YDateList
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
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

    // 登录
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("login_type")
        loginType: Int = 4,
        @Field("code")
        code: String = "",
        @Field("account")
        account: String,
        @Field("access_token")
        accessToken: String = ""
    ): ApiResponse<Any>

    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST("sendVerificationCode")
    suspend fun sendCode(
        @Field("mobile") mobile: String
    ): ApiResponse<Any>
}
