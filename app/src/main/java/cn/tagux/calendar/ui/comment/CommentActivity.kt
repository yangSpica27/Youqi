package cn.tagux.calendar.ui.comment

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.lifecycle.lifecycleScope
import cn.tagux.calendar.R
import cn.tagux.calendar.base.BindingActivity
import cn.tagux.calendar.databinding.ActivityCommentBinding
import cn.tagux.calendar.tools.doOnMainThreadIdle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.effective.android.panel.PanelSwitchHelper
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

@AndroidEntryPoint
class CommentActivity : BindingActivity<ActivityCommentBinding>() {

    private val commentAdapter by lazy {
        CommentAdapter()
    }

    private var commentId: Int = 0

    private val viewModel by viewModels<CommentViewModel>()

    // 软键盘协助工具类
    private lateinit var mHelper: PanelSwitchHelper

    override fun initializer() {

        commentId = intent.getIntExtra("cid", 0)

        // 透明状态栏
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        // 下沉一个状态栏的高度以适配沉浸式状态栏
        viewBinding.toolbar.apply {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                updateMargins(top = marginTop + statusBarHeight)
            }
        }

        // 绑定软键盘和对应view
        mHelper = PanelSwitchHelper.Builder(this)
            .addContentScrollMeasurer {
                getScrollDistance { defaultDistance -> defaultDistance }
                getScrollViewId { R.id.recycler_view }
            }
            .contentScrollOutsideEnable(true)
            .build(false)

        // cpu空闲时再初始化重任务
        doOnMainThreadIdle({
            initRecyclerView()
        })

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.commentList.collectLatest {
                if (it.isEmpty()) {
                    // 返回空数组表示到底 加载结束
                    commentAdapter.loadMoreModule.loadMoreEnd()
                    return@collectLatest
                }
                // 加添数据加载结束
                commentAdapter.addData(it)
                commentAdapter.loadMoreModule.loadMoreComplete()
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.errorMessage.collectLatest {
                // 加载出现异常
                commentAdapter.loadMoreModule.loadMoreFail()
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.isLoading.collectLatest {
            }
        }

        lifecycleScope.launch {
            viewModel.commentId = commentId
            // 开始网络请求
            viewModel.loadMoreComment(true)
        }
    }

    override fun onBackPressed() {
        // 用户按下返回键的时候，如果显示面板，则需要隐藏
        if (mHelper.hookSystemBackByPanelSwitcher()) {
            return
        }
        super.onBackPressed()
    }

    private fun initRecyclerView() {
        commentAdapter.setEmptyView(R.layout.layout_comment_empty)
        commentAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn)

        // 添加分割线
        dividerBuilder()
            .colorRes(R.color.line_divider)
            .size(2)
            .build()
            .addTo(viewBinding.recyclerView)

        OverScrollDecoratorHelper.setUpOverScroll(
            viewBinding.recyclerView,
            OverScrollDecoratorHelper.ORIENTATION_VERTICAL
        )

        commentAdapter.setDiffCallback(DiffCommentCallBack())
        viewBinding.recyclerView.adapter = commentAdapter
        commentAdapter.loadMoreModule.setOnLoadMoreListener {
            viewModel.loadMoreComment(false)
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater):
        ActivityCommentBinding = ActivityCommentBinding.inflate(inflater)
}
