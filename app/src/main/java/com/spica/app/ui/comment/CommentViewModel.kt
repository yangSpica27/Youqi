package com.spica.app.ui.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spica.app.model.comment.CommentItem
import com.spica.app.repository.YRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val yRepostory: YRepostory
) : ViewModel() {

    // 是否正在加载
    val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    // 错误消息
    val errorMessage: MutableStateFlow<String?> = MutableStateFlow("")

    // 页码
    private val currentPage: MutableStateFlow<Int> = MutableStateFlow(-1)

    // cid
    var commentId = 0

    private val _commentList: Flow<List<CommentItem>> =
        currentPage
            .filter {
                it > 0
            }
            .flatMapLatest { page ->
                yRepostory
                    .getCommentList(
                        page = page,
                        contentId = commentId,
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
                        }
                    ).stateIn(
                        scope = viewModelScope, //作用域
                        started = SharingStarted.WhileSubscribed(5000),//等待时间
                        initialValue = listOf() //默认值
                    )
            }


    val commentList: Flow<List<CommentItem>>
        get() = _commentList


    /**
     * 加载主页的文章
     */
    fun loadMoreComment(isRefresh: Boolean) {
        if (isRefresh) {
            currentPage.value = 1
        } else {
            currentPage.value++
        }
    }

}