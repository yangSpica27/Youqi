package cn.tagux.calendar.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.tagux.calendar.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


    // 是否正在加载
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    // 错误消息
    val errorMessage: MutableStateFlow<String?> = MutableStateFlow("")

    private lateinit var loginFlow:Flow<Any>

    /**
     * 登陆
     */
    fun loginIn(openid:String,accessToken:String){
        loginFlow = userRepository.loginIn(openid,accessToken, onComplete = {},
        onError = {}, onStart = {}, onSuccess = {}).stateIn(
            scope = viewModelScope, //作用域
            started = SharingStarted.WhileSubscribed(5000),//等待时间
            initialValue = Any() //默认值
        )
    }





}