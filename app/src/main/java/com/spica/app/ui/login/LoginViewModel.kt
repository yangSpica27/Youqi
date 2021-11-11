package com.spica.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spica.app.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
  private val loginRepository: LoginRepository
) : ViewModel() {


  /**
   * 登录
   */
  fun login(
    userName: String,
    password: String,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit,
    onSuccess: () -> Unit
  ) {
    viewModelScope.launch {
      loginRepository.login(
        userName,
        password,
        onStart,
        onComplete,
        onError,
        onSuccess
      ).collect()
    }
  }


  /**
   * 注册
   */
  fun register(
    userName: String,
    password: String,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onError: (String?) -> Unit,
    onSuccess: () -> Unit
  ) {
    viewModelScope.launch {
      loginRepository.register(
        userName,
        password,
        onStart,
        onComplete,
        onError,
        onSuccess
      ).collect()
    }
  }


  //验证密码
  fun verifyPassword(password: String, rePassword: String) = password == rePassword


}