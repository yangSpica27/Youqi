package com.spica.app.ui.config

import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.gyf.immersionbar.ktx.immersionBar
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityConfigBinding
import com.spica.app.model.ConfigItem
import com.spica.app.tools.doOnMainThreadIdle

/**
 * 配置页面
 */
class ConfigActivity : BindingActivity<ActivityConfigBinding>() {


    private val adapter by lazy {
        ConfigAdapter()
    }


    override fun initializer() {

        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

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