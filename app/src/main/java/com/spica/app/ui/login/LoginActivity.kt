package com.spica.app.ui.login

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityLoginBinding
import com.spica.app.tools.keyboard.FluidContentResizer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * 登录Activity
 */
@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>() {

  private val viewModel: LoginViewModel by viewModels()

  private val enterTransform by lazy {
    MaterialContainerTransform().apply {
      startView = viewBinding.panelLogin
      endView = viewBinding.panelRegister
      addTarget(endView)
      pathMotion = MaterialArcMotion()
      scrimColor = Color.TRANSPARENT
    }
  }

  private val exitTransform by lazy {
    MaterialContainerTransform().apply {
      startView = viewBinding.panelRegister
      endView = viewBinding.panelLogin
      addTarget(endView)
      pathMotion = MaterialArcMotion()
      scrimColor = Color.TRANSPARENT
    }

  }

  /**
   * 进入注册页面的动画
   */
  private fun enterRegister() {
    TransitionManager.beginDelayedTransition(
      findViewById(android.R.id.content),
      enterTransform
    )
    viewBinding.panelLogin.visibility = View.GONE
    viewBinding.panelRegister.visibility = View.VISIBLE
  }

  /**
   * 退出注册页面的动画
   */
  private fun exitRegister() {
    TransitionManager.beginDelayedTransition(
      findViewById(android.R.id.content),
      exitTransform
    )
    viewBinding.panelRegister.visibility = View.GONE
    viewBinding.panelLogin.visibility = View.VISIBLE
  }


  override fun initializer() {
    //软键盘适配
    FluidContentResizer.listen(this)
    initView()
  }

  override fun setupViewBinding(inflater: LayoutInflater):
      ActivityLoginBinding =
    ActivityLoginBinding.inflate(inflater)


  private fun initView() {
    viewBinding.btnEnterLogin.setOnClickListener { exitRegister() }
    viewBinding.btnEnterRegister.setOnClickListener { enterRegister() }
    viewBinding.btnEnterLogin2.setOnClickListener { exitRegister() }

    viewBinding.btnLogin.setOnClickListener {
      if (viewBinding.etAccount.text.toString().isEmpty() ||
        viewBinding.etPassword.text.toString().isEmpty()
      ) {
        showSnackbar("账号&密码不能为空")
        return@setOnClickListener
      }

      Timber.e("登录")
      viewModel.login(
        viewBinding.etAccount.text.toString(),
        viewBinding.etPassword.text.toString(),
        onStart = {
          Timber.e("登录:start")
          lifecycleScope.launch(Dispatchers.Main) {
            viewBinding.btnEnterRegister.isClickable = false
            viewBinding.btnLogin.text = "登录中..."
            viewBinding.btnLogin.isClickable = false
          }
        },
        onComplete = {
          Timber.e("登录:complete")
          lifecycleScope.launch(Dispatchers.Main) {
            viewBinding.btnLogin.text = "登录"
            viewBinding.btnEnterRegister.isClickable = true
            viewBinding.btnLogin.isClickable = true
          }
        },
        onError = {
          Timber.e("登录:error")
          lifecycleScope.launch(Dispatchers.Main) {
            it?.let { showSnackbar(it) }
          }
        },
        onSuccess = {
          lifecycleScope.launch(Dispatchers.Main) {
            showSnackbar("登录成功")
            finish()
          }
        }
      )

    }
    viewBinding.btnRegister.setOnClickListener {

      if (viewBinding.etRAccount.text.toString().isEmpty()) {
        showSnackbar("账号不能为空")
        return@setOnClickListener
      }

      if (!viewModel.verifyPassword(
          viewBinding.etReRPassword.text.toString(),
          viewBinding.etRPassword.text.toString()
        )
      ) {
        showSnackbar("密码不匹配")
        return@setOnClickListener
      }

      viewModel.register(
        viewBinding.etRAccount.text.toString(),
        viewBinding.etRPassword.text.toString(),
        onStart = {
          lifecycleScope.launch(Dispatchers.Main) {
            viewBinding.btnEnterLogin.isClickable = false
            viewBinding.btnRegister.isClickable = false
            viewBinding.btnRegister.text = "正在等待结果返回"
          }
        },
        onError = {
          lifecycleScope.launch(Dispatchers.Main) {
            it?.let { showSnackbar(it) }
          }
        },
        onComplete = {
          lifecycleScope.launch(Dispatchers.Main) {
            viewBinding.btnEnterLogin.isClickable = true
            viewBinding.btnRegister.isClickable = true
            viewBinding.btnRegister.text = "注册"
          }
        },
        onSuccess = {
          lifecycleScope.launch(Dispatchers.Main) {
            showSnackbar("注册完成")
          }
        }
      )

    }

    viewBinding.etReRPassword.addTextChangedListener(
      object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(p0: Editable?) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
          //验证密码是否匹配
          if (viewBinding.etRPassword.text.isNullOrEmpty()) {
            viewBinding.layoutReRPassword.isErrorEnabled = false
          }
          if (viewBinding.etRPassword.text.toString() !=
            viewBinding.etReRPassword.text.toString()
          ) {
            if (!viewBinding.layoutReRPassword.isErrorEnabled) {
              viewBinding.layoutReRPassword.isErrorEnabled = true
            }
            Timber.e("密码不匹配")
            viewBinding.layoutReRPassword.error = "密码不匹配"
          } else {
            Timber.e("密码匹配")
            viewBinding.layoutReRPassword.isErrorEnabled = false
          }
        }

      })

  }


  private fun showSnackbar(info: String) {
    Snackbar.make(viewBinding.root, info, Snackbar.LENGTH_SHORT)
      .show()
  }


}