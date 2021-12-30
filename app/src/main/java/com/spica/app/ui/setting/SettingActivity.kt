package com.spica.app.ui.setting

import android.view.LayoutInflater
import com.gyf.immersionbar.ktx.immersionBar
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivitySettingBinding
import com.spica.app.widget.CurtainView
import dagger.hilt.android.AndroidEntryPoint

/**
 * 设置页
 */
@AndroidEntryPoint
class SettingActivity : BindingActivity<ActivitySettingBinding>() {


    override fun initializer() {
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }
    }


    override fun setupViewBinding(inflater: LayoutInflater):
            ActivitySettingBinding = ActivitySettingBinding.inflate(inflater)



}