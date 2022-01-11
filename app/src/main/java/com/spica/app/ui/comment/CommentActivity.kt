package com.spica.app.ui.comment

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.lifecycle.lifecycleScope
import com.chad.library.adapter.base.BaseQuickAdapter
import com.effective.android.panel.PanelSwitchHelper
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityCommentBinding
import com.spica.app.tools.doOnMainThreadIdle
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

        //透明状态栏
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        viewBinding.toolbar.apply {
            updateLayoutParams<ConstraintLayout.LayoutParams> {
                updateMargins(top = marginTop + statusBarHeight)
            }
        }


        mHelper = PanelSwitchHelper.Builder(this)
            .addContentScrollMeasurer {
                getScrollDistance { defaultDistance -> defaultDistance }
                getScrollViewId { R.id.recycler_view }
            }
            .contentScrollOutsideEnable(true)
            .build(false)


        doOnMainThreadIdle({
            initRecyclerView()
        })



        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.commentList.collectLatest {
                if (it.isEmpty()) {
                    commentAdapter.loadMoreModule.loadMoreEnd()
                    return@collectLatest
                }
                commentAdapter.addData(it)
                commentAdapter.loadMoreModule.loadMoreComplete()
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.errorMessage.collectLatest {
                commentAdapter.loadMoreModule.loadMoreFail()
            }
        }


        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.isLoading.collectLatest {

            }
        }

        lifecycleScope.launch {
            viewModel.commentId = commentId
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