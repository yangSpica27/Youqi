package com.spica.app.network.service

import com.skydoves.sandwich.ApiResponse
import com.spica.app.model.article.Article
import com.spica.app.model.banner.Banner
import com.spica.app.model.chapters.Chapters
import com.spica.app.model.navigation.Navigation
import com.spica.app.model.system.SystemType
import com.spica.app.model.user.User
import retrofit2.http.*

@Suppress("unused")
interface ApiService {

  companion object {
    const val BASE_URL = "https://www.wanandroid.com"
  }

  /**
   * 获取首页Banner
   */
  @GET("/banner/json")
  suspend fun getBanner(): ApiResponse<Banner>


  /**
   * 登录
   */
  @FormUrlEncoded
  @POST("/user/login")
  suspend fun login(
    @Field("username") userName: String,
    @Field("password") passWord: String
  ): ApiResponse<User>


  /**
   * 注册
   */
  @FormUrlEncoded
  @POST("/user/register")
  suspend fun register(
    @Field("username") userName: String,
    @Field("password") passWord: String,
    @Field("repassword") rePassWord: String
  ): ApiResponse<User>


  /**
   * 登出
   */
  @GET("/user/logout/json")
  suspend fun logOut(): ApiResponse<Any>


  /**
   * 获取首页文章列表
   */
  @GET("/article/list/{page}/json")
  suspend fun getHomeArticles(@Path("page") page: Int): ApiResponse<Article>

  /**
   * 获取广场文章列表
   */
  @GET("/user_article/list/{page}/json")
  suspend fun getSquareArticleList(@Path("page") page: Int): ApiResponse<Article>


  /**
   * 获取最新项目
   */
  @GET("/article/listproject/{page}/json")
  suspend fun getLastedProject(@Path("page") page: Int): ApiResponse<Article>


  /**
   * 获取系统结构树
   */
  @GET("/tree/json")
  suspend fun getSystemType(): ApiResponse<SystemType>

  /**
   * 获取所有导航
   */
  @GET("/navi/json")
  suspend fun getNavigation(): ApiResponse<Navigation>

  /**
   * 获取最新项目
   */
  @GET("https://wanandroid.com/article/listproject/{page}/json")
  suspend fun getUpdateProject(@Path("page") page: Int): ApiResponse<Article>


  /**
   * 获取体系结构 单元具体内容列表
   */
  @GET("/article/list/{page}/json")
  suspend fun getSystemTypeDetail(@Path("page") page: Int, @Query("cid") cid: Int):
      ApiResponse<SystemType>

  /**
   * 获取微信公众号的列表
   */
  @GET("/wxarticle/chapters/json")
  suspend fun getWxArticleChapters(): ApiResponse<Chapters>


  /**
   * 某个公众号的历史数据
   */
  @GET("/wxarticle/list/{id}/{page}/json")
  suspend fun getWxArticles(
    @Path("id") id: Int,
    @Path("page") page: Int
  ): ApiResponse<Article>


  /**
   * 获取项目类型列表
   */
  @GET("/project/tree/json")
  suspend fun getProjectTypes(): ApiResponse<Chapters>


  /**
   * 获取项目列表
   */
  @GET("/project/list/{page}/json")
  suspend fun getProjects(
    @Path("page") page: Int,
    @Query("cid") id: Int,
  ): ApiResponse<Article>




}