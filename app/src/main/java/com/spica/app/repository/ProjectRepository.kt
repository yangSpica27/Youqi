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

class ProjectRepository
@Inject
constructor(
  private val wanAndroidClient: WanAndroidClient,
) : Repository {

  /**
   * 获取项目类型列表
   */
  fun getTabs(
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    val response = wanAndroidClient.fetchProjectTypes()

    response.suspendOnSuccess { emit(data.data) }
      .suspendOnException { onError(message) }
      .suspendOnError { onError(message()) }
  }.onStart { onStart() }
    .onCompletion { onComplete() }
    .flowOn(Dispatchers.IO)

  /**
   * 获取指定类型的项目列表
   */
  fun getProjects(
    page: Int,
    id: Int,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    val response = wanAndroidClient.fetchProjectsWithType(page = page, id = id)
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