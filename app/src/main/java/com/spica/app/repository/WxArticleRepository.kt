package com.spica.app.repository

import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.spica.app.network.WanAndroidClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class WxArticleRepository @Inject
constructor(
  private val wanAndroidClient: WanAndroidClient,
) : Repository {

  /**
   * 获取公众号列表
   */
  fun getWxArticleChapters(
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    val response = wanAndroidClient.fetchWxArticleChapters()

    response.suspendOnSuccess { emit(data.data) }
      .suspendOnException { onError(message) }
      .suspendOnError { onError(message()) }
  }.onStart { onStart() }
    .onCompletion { onComplete() }
    .flowOn(Dispatchers.IO)

  /**
   * 获取指定微信公众号的文章
   */
  fun getWxArticles(
    page: Int,
    id: Int,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    val response = wanAndroidClient.fetchWxArticles(page = page, id = id)

    response.suspendOnSuccess {
      emit(data.articleData.datas)
    }.suspendOnException {
      onError(message)
    }.suspendOnError {
      onError(message())
    }

  }.onStart { onStart() }
    .onCompletion { onComplete() }
    .flowOn(Dispatchers.IO)


}