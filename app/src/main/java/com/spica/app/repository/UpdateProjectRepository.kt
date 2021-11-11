package com.spica.app.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.spica.app.network.WanAndroidClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class UpdateProjectRepository
@Inject constructor(private val wanAndroidClient: WanAndroidClient) : Repository {

  /**
   *  获取首页文章
   */
  @WorkerThread
  fun fetchUpdateProjects(
    page: Int,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    val response = wanAndroidClient.fetchUpdateProject(page)
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