package cn.tagux.calendar.ui.setting

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import cn.tagux.calendar.R
import cn.tagux.calendar.base.BindingActivity
import cn.tagux.calendar.databinding.ActivitySettingBinding
import cn.tagux.calendar.tools.Preference
import cn.tagux.calendar.tools.doOnMainThreadIdle
import cn.tagux.calendar.ui.config.ConfigActivity
import cn.tagux.calendar.ui.login.LoginActivity
import cn.tagux.calendar.ui.user.UserActivity
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.gyf.immersionbar.ktx.immersionBar
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