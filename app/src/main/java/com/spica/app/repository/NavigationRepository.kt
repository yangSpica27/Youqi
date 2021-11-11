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
import timber.log.Timber
import javax.inject.Inject

class NavigationRepository @Inject
constructor(private val wanAndroidClient: WanAndroidClient) : Repository {

  fun fetchNavigation(
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit
  ) = flow {
    val response = wanAndroidClient.fetchNavigation()
    response.suspendOnSuccess {
      emit(data.data)
    }.suspendOnException {
      Timber.e(message)
      onError(message)
    }.suspendOnError {
      Timber.e(message())
      onError(message())
    }
  }.onStart {
    onStart()
  }.onCompletion {
    onComplete()
  }
    .flowOn(Dispatchers.IO)


}