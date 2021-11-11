package com.spica.app.repository

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.*
import com.spica.app.model.user.UserData
import com.spica.app.network.WanAndroidClient
import com.spica.app.persistence.dao.UserDao
import com.spica.app.tools.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * 登录Repository
 */
class LoginRepository @Inject constructor(
  private val wanAndroidClient: WanAndroidClient,
  private val userDao: UserDao
) : Repository {


  private var isLogin by Preference(Preference.IS_LOGIN, false)


  /**
   * 登录
   * userName:用户名
   * password：密码
   */
  @WorkerThread
  fun login(
    userName: String,
    password: String,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit,
    onSuccess: () -> Unit
  ) = flow {
    var userData: UserData

    val response = wanAndroidClient.login(userName, password)

    response.suspendOnSuccess {
      if (this.data.errorCode != 0) {
        onError(data.errorMsg)
      } else {
        this.data.userData?.let {
          userData = it
          userDao.deleteUser()
          userDao.insert(userData)
          emit(userData)
          isLogin = true
          onSuccess()
        }
      }

    }.onError {
      onError(message())
    }
      .onException {
        onError(message)
      }
  }.onStart {
    onStart()
  }.onCompletion {
    onComplete()
  }.flowOn(Dispatchers.IO)


  /**
   * 注册
   * userName:用户名
   * password：密码
   */
  fun register(
    userName: String,
    password: String,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit,
    onSuccess: () -> Unit
  ) = flow {
    var userData: UserData
    val response = wanAndroidClient.register(userName, password)
    response.suspendOnSuccess {
      if (data.errorCode != 0) {
        onError(data.errorMsg)
      } else {
        this.data.userData?.let {
          userData = it
          userDao.deleteUser()
          userDao.insert(userData)
          emit(userData)
          onSuccess()
        }
      }
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


  /**
   * 登出
   */
  suspend fun logout(
    onComplete: () -> Unit,
    onError: (String?) -> Unit,
  ) {
    val response = wanAndroidClient.logout()
    response.suspendOnSuccess {
      //清除用户数数据
      userDao.deleteUser()
      onComplete()//触发结束回调
    }.suspendOnException {
      onError(message)
    }.suspendOnError {
      onError(message())
    }
  }


}