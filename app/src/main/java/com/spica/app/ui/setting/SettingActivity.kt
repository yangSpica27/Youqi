package com.spica.app.ui.setting

import android.view.LayoutInflater
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivitySettingBinding

/**
 * 设置页
 */
class SettingActivity : BindingActivity<ActivitySettingBinding>() {


    override fun initializer() {

    }


    override fun setupViewBinding(inflater: LayoutInflater):
            ActivitySettingBinding = ActivitySettingBinding.inflate(inflater)
}