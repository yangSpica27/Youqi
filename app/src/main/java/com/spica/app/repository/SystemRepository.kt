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

/**
 * 体系 Repository
 */
class SystemRepository @Inject constructor(

  private val wanAndroidClient: WanAndroidClient

) : Repository {


  /**
   * 获取体系的tree
   */
  fun getSystemtree(
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    val repository = wanAndroidClient.fetchSystemTypes()
    repository.suspendOnSuccess {
      emit(data.data)
    }.suspendOnException {
      onError(message)
    }.suspendOnError {
      onError(message())
    }
  }.onStart {
    onStart()
  }
    .onCompletion {
      onComplete()
    }.flowOn(Dispatchers.IO)


  /**
   * 获取对应的文章列表
   */
  fun getSystemArticles() {

  }


}