package com.spica.app.ui.login


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.kongzue.dialogx.dialogs.FullScreenDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityLoginBinding
import com.spica.app.tools.keyboard.FluidContentResizer
import com.spica.app.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 登录页面
 */
@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>() {



    private val loginDialog by lazy {
        FullScreenDialog.build(object : OnBindView<FullScreenDialog?>(R.layout.dialog_login) {
            override fun onBind(dialog: FullScreenDialog?, v: View?) {
                v?.let {

                }
            }
        })
    }


    override fun initializer() {

        // 沉浸式状态栏
        immersionBar() {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        FluidContentResizer.listen(this)

        // 点击登录
        viewBinding.btnLogin.setOnClickListener {
            loginDialog.show(this)
        }

        // 点击直接进入
        viewBinding.btnEnter.setOnClickListener {
            startActivity(Intent(this@LoginActivity,
                MainActivity::class.java))
        }


    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

}