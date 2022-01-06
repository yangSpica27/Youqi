package com.spica.app.ui.comment

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import com.effective.android.panel.PanelSwitchHelper
import com.fondesa.recyclerviewdivider.Divider
import com.fondesa.recyclerviewdivider.Grid
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.fondesa.recyclerviewdivider.visibility.VisibilityProvider
import com.google.android.material.appbar.MaterialToolbar
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityCommentBinding
import com.spica.app.extensions.dp


class CommentActivity : BindingActivity<ActivityCommentBinding>() {

    private val commentAdapter by lazy {
        CommentAdapter()
    }

    // 软键盘协助工具类
    private lateinit var mHelper: PanelSwitchHelper

    override fun initializer() {
        //透明状态栏
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        mHelper = PanelSwitchHelper.Builder(this)
            .addContentScrollMeasurer {
                getScrollDistance { defaultDistance -> defaultDistance }
                getScrollViewId { R.id.recycler_view }
            }
            .contentScrollOutsideEnable(true)
            .build(true)
        val data = arrayListOf<Any>()

        for (index in 1..100) {
            data.add(index)
        }
        commentAdapter.addData(data)
        // 头部
        commentAdapter.addHeaderView(MaterialToolbar(this).apply {
            isTitleCentered = true
            title = "用户评论"
            layoutParams = LinearLayout
                .LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    updateMargins(top = ImmersionBar.getStatusBarHeight(this@CommentActivity))
                }

        })
        commentAdapter.addFooterView(
            TextView(this).apply {
                gravity = Gravity.CENTER
                textSize = 14F
                text = "- 已经到底了- "
                updatePadding(top = 12.dp, bottom = 12.dp)
            }
        )
        commentAdapter.setEmptyView(R.layout.layout_comment_empty)
        dividerBuilder()
            .colorRes(R.color.line_divider)
            .size(2)
            .build()
            .addTo(viewBinding.recyclerView)

        viewBinding.recyclerView.adapter = commentAdapter
    }


    override fun onBackPressed() {
        // 用户按下返回键的时候，如果显示面板，则需要隐藏
        if (mHelper.hookSystemBackByPanelSwitcher()) {
            return
        }
        super.onBackPressed()
    }


    override fun setupViewBinding(inflater: LayoutInflater):
            ActivityCommentBinding = ActivityCommentBinding.inflate(inflater)

}