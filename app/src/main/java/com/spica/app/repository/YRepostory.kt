package com.spica.app.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import com.spica.app.model.YDateList
import com.spica.app.network.AppClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class YRepostory
@Inject constructor(
    private val appClient: AppClient
) {


    @WorkerThread
    fun dateList(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        onSuccess: () -> Unit
    ) = flow {

        var yDateList: YDateList

        val response = appClient.getDateList()

        response.suspendOnSuccess {
            yDateList = this.data
            emit(yDateList)
        }.onError {
            onError(message())
        }.onException {
            onError(message)
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }.flowOn(Dispatchers.IO)

}