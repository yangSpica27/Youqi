package com.spica.app.network

import com.skydoves.sandwich.ApiResponse
import com.spica.app.model.article.Article
import com.spica.app.model.banner.Banner
import com.spica.app.model.chapters.Chapters
import com.spica.app.model.navigation.Navigation
import com.spica.app.model.system.SystemType
import com.spica.app.model.user.User
import com.spica.app.network.service.ApiService
import javax.inject.Inject


/**
 * 网络请求的Client
 */
class WanAndroidClient
@Inject constructor(
  private val apiService: ApiService
) {
  suspend fun fetchBanner():
      ApiResponse<Banner> = apiService.getBanner()

  suspend fun login(userName: String, password: String):
      ApiResponse<User> = apiService.login(userName, password)


  suspend fun logout(): ApiResponse<Any> = apiService.logOut()

  suspend fun register(userName: String, password: String):
      ApiResponse<User> = apiService.register(userName, password, password)

  suspend fun fetchHomeArticles(page: Int): ApiResponse<Article> =
    apiService.getHomeArticles(page)

  suspend fun fetchSquareArticles(page: Int): ApiResponse<Article> =
    apiService.getSquareArticleList(page)

  suspend fun fetchSystemTypes(): ApiResponse<SystemType> =
    apiService.getSystemType()

  suspend fun fetchNavigation(): ApiResponse<Navigation> =
    apiService.getNavigation()

  suspend fun fetchUpdateProject(page: Int): ApiResponse<Article> =
    apiService.getUpdateProject(page)

  suspend fun fetchWxArticleChapters(): ApiResponse<Chapters> =
    apiService.getWxArticleChapters()

  suspend fun fetchWxArticles(id: Int, page: Int): ApiResponse<Article> =
    apiService.getWxArticles(id, page)


  suspend fun fetchProjectTypes(): ApiResponse<Chapters> =
    apiService.getProjectTypes()


  suspend fun fetchProjectsWithType(id: Int, page: Int): ApiResponse<Article> =
    apiService.getProjects(page, id)


}