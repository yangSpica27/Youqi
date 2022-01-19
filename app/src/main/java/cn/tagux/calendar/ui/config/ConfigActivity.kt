package cn.tagux.calendar.ui.config

import android.view.LayoutInflater
import cn.tagux.calendar.R
import cn.tagux.calendar.base.BindingActivity
import cn.tagux.calendar.databinding.ActivityConfigBinding
import cn.tagux.calendar.model.ConfigItem
import cn.tagux.calendar.tools.doOnMainThreadIdle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.gyf.immersionbar.ktx.immersionBar

/**
 * 配置页面
 */
class ConfigActivity : BindingActivity<ActivityConfigBinding>() {

    private val adapter by lazy {
        ConfigAdapter()
    }

    override fun initializer() {

        // 沉浸式状态栏
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        // 线程空闲时再初始化列表
        doOnMainThreadIdle(
            { initRecyclerview() }
        )
    }

    private fun initRecyclerview() {
        adapter.addData(
            mutableListOf(
                ConfigItem(1L, "清除缓存", "0MB"),
                ConfigItem(2L, "分享应用", ">"),
                ConfigItem(3L, "给我们打分", ">")
            )
        )
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
        adapter.animationEnable = true
        // 添加分割线
        dividerBuilder()
            .colorRes(R.color.line_divider)
            .size(2)
            .build()
            .addTo(viewBinding.rvConfig)
        viewBinding.rvConfig.adapter = adapter

        adapter.setOnItemClickListener { _, view, position ->
            kotlin.run {
            }
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityConfigBinding =
        ActivityConfigBinding.inflate(inflater)
}
