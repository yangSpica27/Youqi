package com.spica.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.spica.app.model.user.UserData
import com.spica.app.persistence.dao.UserDao
import com.spica.app.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
  userDao: UserDao,
  val repository: LoginRepository
) : ViewModel() {


  private var _user: LiveData<UserData?>

  val user: LiveData<UserData?>
    get() = _user

  init {
    Timber.d("初始化：MainViewModel")
    _user = userDao.queryUserLiveDate()
      .distinctUntilChanged()//过滤其中重复的消息
  }


  /**
   * 登出
   */
 fun logout(
    onComplete: () -> Unit,
    onError: (String?) -> Unit,
  ) {
    viewModelScope.launch {
      repository.logout(onComplete, onError)
    }
  }


}