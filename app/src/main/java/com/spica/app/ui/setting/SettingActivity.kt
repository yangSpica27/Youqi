package com.spica.app.ui.setting

import android.view.LayoutInflater
import com.gyf.immersionbar.ktx.immersionBar
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivitySettingBinding

/**
 * 设置页
 */
class SettingActivity : BindingActivity<ActivitySettingBinding>() {


    override fun initializer() {
        immersionBar() {
            statusBarColor(R.color.white)
        }
    }


    override fun setupViewBinding(inflater: LayoutInflater):
            ActivitySettingBinding = ActivitySettingBinding.inflate(inflater)
}