package com.spica.app.ui.main

import android.view.LayoutInflater
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * 主页面
 */
@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {


    override fun initializer() {

    }

    override fun setupViewBinding(inflater: LayoutInflater):
            ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}
