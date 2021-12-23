package com.spica.app.ui.login


import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.kongzue.dialogx.dialogs.FullScreenDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityLoginBinding
import com.spica.app.databinding.DialogLoginBinding
import com.spica.app.tools.keyboard.FluidContentResizer
import com.spica.app.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 登录页面
 */
@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>() {


    private var lastString = ""// 记录输入前的字符内容

    private var cIndex = 0 // 记录数日前的光标位置

    private val loginDialog by lazy {
        FullScreenDialog.build(object : OnBindView<FullScreenDialog?>(R.layout.dialog_login) {
            override fun onBind(dialog: FullScreenDialog?, v: View?) {
                v?.let {
                    val dialogBinding = DialogLoginBinding.bind(it)
                    dialogBinding.etUserName.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
                            lastString = text.toString()
                            cIndex = start
                        }

                        override fun onTextChanged(
                            text: CharSequence,
                            start: Int, befored: Int, count: Int
                        ) {
                            //假设最大输入4个 把判断改成判断字节数就行
                            if (text.length > 4) {
                                // 超出即取消这次输入,返回输入前的状态,即是lastString
                                dialogBinding.etUserName.setText(lastString)
                                // 恢复光标位置
                                dialogBinding.etUserName.setSelection(start)
                            }
                        }

                        override fun afterTextChanged(ed: Editable) = Unit

                    })
                }

            }
        })
    }


    override fun initializer() {

        immersionBar() {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        FluidContentResizer.listen(this)

        viewBinding.btnLogin.setOnClickListener {
            loginDialog.show(this)
        }
        viewBinding.btnEnter.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }


    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

}