package com.spica.app.ui.wxarticlecontainer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spica.app.repository.WxArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class WxArticleViewModel
@Inject constructor(
  private val wxArticleRepository: WxArticleRepository
) : ViewModel() {

  //是否正在加载
  private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

  //错误消息
  private val errorMessage: MutableStateFlow<String?> = MutableStateFlow("")


  private val page = MutableStateFlow(-1)

  private val id = MutableStateFlow(-1)


  val articleFlow = page
    .filter {
      return@filter ((page.value != -1) && id.value != -1)
    }
    .flatMapLatest { page ->
      wxArticleRepository.getWxArticles(page = page, id = id.value,
        onStart = { isLoading.value = true },
        onComplete = { isLoading.value = false },
        onError = { errorMessage.value = it }
      )
    }

  //微信公众号博主列表流
  val chapterFlow = wxArticleRepository
    .getWxArticleChapters(
      onStart = { isLoading.value = true },
      onComplete = { isLoading.value = false },
      onError = { errorMessage.value = it }
    )
    .stateIn(
      scope = viewModelScope, //作用域
      started = SharingStarted.WhileSubscribed(5000),//等待时间
      initialValue = listOf()//默认值
    )


  fun getArticles(id: Int) {
    page.value++
    this.id.value = id
  }


}