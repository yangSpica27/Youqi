package com.spica.app.ui.system

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spica.app.model.system.SystemData
import com.spica.app.repository.SystemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SystemViewModel @Inject
constructor(repository: SystemRepository) : ViewModel() {


  //是否正在加载
  private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)


  private val errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)

  val isLoading: StateFlow<Boolean>
    get() = _isLoading

  val systemTypeFlow: StateFlow<List<SystemData>> = repository.getSystemtree(
    onError = { errorMessage.value = it },
    onComplete = { _isLoading.value = false },
    onStart = { _isLoading.value = true }
  )
    .stateIn(
      scope = viewModelScope, //作用域
      started = SharingStarted.WhileSubscribed(5000),//等待时间
      initialValue = listOf()//默认值
    )


}