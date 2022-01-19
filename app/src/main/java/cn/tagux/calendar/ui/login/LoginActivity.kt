package cn.tagux.calendar.ui.login


import android.Manifest
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import cn.tagux.calendar.R
import cn.tagux.calendar.base.BindingActivity
import cn.tagux.calendar.databinding.ActivityLoginBinding
import cn.tagux.calendar.databinding.DialogLoginBinding
import cn.tagux.calendar.tools.keyboard.FluidContentResizer
import cn.tagux.calendar.ui.main.MainActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.kongzue.dialogx.dialogs.FullScreenDialog
import com.kongzue.dialogx.interfaces.OnBindView
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

/**
 * 登录页面
 */
@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>() {


    @Inject
    lateinit var tencent: Tencent

    private val viewModel by viewModels<LoginViewModel>()


    private val loginDialog by lazy {
        FullScreenDialog.build(object : OnBindView<FullScreenDialog?>(R.layout.dialog_login) {
            override fun onBind(dialog: FullScreenDialog?, v: View) {
                val binding = DialogLoginBinding.bind(v)
                binding.btnLoginQq.setOnClickListener {

                    if (!tencent.isSessionValid)
                        tencent.login(this@LoginActivity, "all", object : IUiListener {

                            override fun onComplete(jsonObject: Any) {
                                val result = jsonObject as JSONObject
                                val openid = result.optString("openid")
                                val accessToken = result.optString("access_token")
                                viewModel.loginIn(openid, accessToken)
                            }

                            override fun onError(error: UiError) {
                                Timber.e(error.toString())
                                Toast.makeText(this@LoginActivity, "error:"+error.errorMessage, Toast.LENGTH_SHORT).show()
                            }

                            override fun onCancel() {
                                Toast.makeText(this@LoginActivity, "登陆取消", Toast.LENGTH_LONG).show()
                            }


                        })
                }
            }
        })
    }


    private fun checkPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.QUERY_ALL_PACKAGES,
            ),
            1
        )


    }

    override fun initializer() {

        // 沉浸式状态栏
        immersionBar() {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        checkPermission()

        FluidContentResizer.listen(this)

        // 点击登录
        viewBinding.btnLogin.setOnClickListener {
            loginDialog.show(this)
        }

        // 点击直接进入
        viewBinding.btnEnter.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    MainActivity::class.java
                )
            )
        }


    }



    override fun setupViewBinding(inflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Tencent.onActivityResultData(requestCode,requestCode,data,object :IUiListener{
            override fun onComplete(p0: Any?) {
                TODO("Not yet implemented")
            }

            override fun onError(p0: UiError?) {
                TODO("Not yet implemented")
            }

            override fun onCancel() {
                TODO("Not yet implemented")
            }

        })
        super.onActivityResult(requestCode, resultCode, data)
    }
}