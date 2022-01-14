package com.spica.app.ui.setting

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.gyf.immersionbar.ktx.immersionBar
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivitySettingBinding
import com.spica.app.tools.Preference
import com.spica.app.tools.doOnMainThreadIdle
import com.spica.app.ui.config.ConfigActivity
import com.spica.app.ui.login.LoginActivity
import com.spica.app.ui.user.UserActivity
import dagger.hilt.android.AndroidEntryPoint
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * 设置页
 */
@AndroidEntryPoint
class SettingActivity : BindingActivity<ActivitySettingBinding>() {


    private val adapter: CollectAdapter by lazy {
        CollectAdapter()
    }

    // 是否登录过
    private var isLogin by Preference(Preference.IS_LOGIN, false)

    override fun initializer() {
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        doOnMainThreadIdle({
            initRecyclerview()
        })

        viewBinding.btnSetting.setOnClickListener {
            startActivity(Intent(this, ConfigActivity::class.java))
        }

        val clickHeader = View.OnClickListener {
            val intent = if (isLogin) {
                Intent(this, UserActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
        }

        viewBinding.ivAvatar.setOnClickListener(clickHeader)
        viewBinding.tvUserName.setOnClickListener(clickHeader)

    }


    private fun initRecyclerview() {
        adapter.addData(mutableListOf("", "", "", ""))
        // 添加分割线
        dividerBuilder()
            .colorRes(R.color.line_divider)
            .size(2)
            .build()
            .addTo(viewBinding.rvCollect)

        OverScrollDecoratorHelper.setUpOverScroll(
            viewBinding.rvCollect,
            OverScrollDecoratorHelper.ORIENTATION_VERTICAL
        )

        viewBinding.rvCollect.adapter = adapter
    }


    override fun setupViewBinding(inflater: LayoutInflater):
            ActivitySettingBinding =
        ActivitySettingBinding.inflate(inflater)


}