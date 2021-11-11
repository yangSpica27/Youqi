package com.spica.app.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spica.app.model.navigation.NavigationData
import com.spica.app.repository.NavigationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
  navigationRepository: NavigationRepository
) : ViewModel() {


  //是否正在加载
  private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)


  private val errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)

  val isLoading: StateFlow<Boolean>
    get() = _isLoading

  val navigationFLow: StateFlow<List<NavigationData>> =
    navigationRepository.fetchNavigation(
      onError = { errorMessage.value = it },
      onComplete = { _isLoading.value = false },
      onStart = { _isLoading.value = true }
    ).stateIn(
      scope = viewModelScope, //作用域
      started = SharingStarted.WhileSubscribed(5000),//等待时间
      initialValue = listOf()//默认值
    )


}