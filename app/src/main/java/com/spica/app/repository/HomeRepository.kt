package com.spica.app.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.spica.app.model.banner.BannerData
import com.spica.app.network.WanAndroidClient
import com.spica.app.persistence.dao.BannerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class HomeRepository @Inject constructor(
  private val wanAndroidClient: WanAndroidClient,
  private val bannerDao: BannerDao
) : Repository {

  /**
   * 获取Banner列表
   */
  @WorkerThread
  fun fetchBanner(
    onStart: () -> Unit,//请求开始的回调
    onComplete: () -> Unit,//请求结束的回调
    onError: (String?) -> Unit//发生错误的回调：String为错误信息
  ) = flow {
    //从缓存中获取banner
    var banners: List<BannerData?> = bannerDao.getBanners()
    banners?.let {
      emit(it) //缓存中有数据 则先返回缓存中的数据给UI更新
      val response = wanAndroidClient.fetchBanner()
      response.suspendOnSuccess {
        //请求成功将数据存入缓存
        banners = data.data
        bannerDao.deleteAllBanner()
        bannerDao.insertBanner(data.data)
        //将请求成功的数据给UI更新
        emit(banners)
      }.onError {
        //发生错误触发错误回调
        onError("${message()}")
      }.onException { //服务端异常触发错误回调
        onError(message)
      }
    }
  }.onStart {
    //请求开始
    onStart()
  }.onCompletion {
    //请求结束
    onComplete()
    //指定线程环境为IO线程
  }.flowOn(Dispatchers.IO)


  /**
   *  获取首页文章
   */
  @WorkerThread
  fun fetchHomeArticle(
    page: Int,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    val response = wanAndroidClient.fetchHomeArticles(page)
    response.suspendOnSuccess {
      //请求成功
      emit(data.articleData)
    }
      .onError {
        onError(message())
      }.onException {
        onError(message)
      }
  }.onStart { onStart() }
    .onCompletion {
    onComplete()
  }.flowOn(Dispatchers.IO)

}