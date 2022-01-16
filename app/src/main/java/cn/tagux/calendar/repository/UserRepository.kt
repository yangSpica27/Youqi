package cn.tagux.calendar.repository

import cn.tagux.calendar.network.AppClient
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val appClient: AppClient
) {

    /**
     *  登陆
     */
    fun loginIn(
        openid: String,
        accessToken: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        onSuccess: () -> Unit
    ) = flow {

        val response = appClient.login(openid, accessToken)
        response.suspendOnSuccess {
            emit(this.data)
            onSuccess()
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


    fun loginOut() {

    }


}