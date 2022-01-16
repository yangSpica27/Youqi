package cn.tagux.calendar.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.tagux.calendar.model.date.YDateList
import cn.tagux.calendar.repository.YRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val yRepostory: YRepostory
) : ViewModel() {

    // 是否正在加载
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    // 错误消息
    val errorMessage: MutableStateFlow<String?> = MutableStateFlow("")


    private lateinit var _dateListFlow: StateFlow<YDateList?>

    val dateList: StateFlow<YDateList?>
        get() = _dateListFlow

    suspend fun dateList() = withContext(Dispatchers.Main) {
        _dateListFlow = yRepostory.dateList(
            onStart = {
                isLoading.value = true
            },
            onComplete = {
                isLoading.value = false
            },
            onError = {
                Timber.e(it)
                isLoading.value = false
                errorMessage.value = it
            },
            onSuccess = {

            }
        ).stateIn(
            scope = viewModelScope, //作用域
            started = SharingStarted.WhileSubscribed(5000),//等待时间
            initialValue = null //默认值
        )
    }

}