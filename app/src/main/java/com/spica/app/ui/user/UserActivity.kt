package com.spica.app.ui.user

import android.view.LayoutInflater
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityUserBinding

/**
 * 用户信息编辑页面
 */
class UserActivity : BindingActivity<ActivityUserBinding>() {


    override fun initializer() {

    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityUserBinding =
        ActivityUserBinding.inflate(inflater)
}