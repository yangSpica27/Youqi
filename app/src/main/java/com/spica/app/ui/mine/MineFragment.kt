package com.spica.app.ui.mine

import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.spica.app.R
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.FragmentMineBinding
import com.spica.app.tools.Preference
import com.spica.app.ui.login.LoginActivity
import com.spica.app.ui.main.MainViewModel
import com.spica.app.ui.userinfo.UserInfoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * 我的
 */
@AndroidEntryPoint
class MineFragment : BindingFragment<FragmentMineBinding>() {

  private var isLogin by Preference(Preference.IS_LOGIN, false)

  private val viewModel: MainViewModel by activityViewModels()

  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      FragmentMineBinding = FragmentMineBinding.inflate(inflater, container, false)

  override fun init() {
    viewBinding.itemWrite.setOnClickListener {
      //点击写作

    }
    viewBinding.itemExit.setOnClickListener {
      //点击退出
      viewModel.logout(
        onComplete = {
          isLogin = false
          lifecycleScope.launch(Dispatchers.IO) {
            showToast("退出成功")
            viewBinding.avatar.load(R.drawable.ic_default_avatar)
            viewBinding.tvUsername.text = "未登录"
            viewBinding.tvEmail.text = "点击登录"
          }
        },
        onError = {
          showToast(it)
        })
    }
    viewBinding.itemCollect.setOnClickListener {
      //点击收藏
    }
    viewBinding.itemFriend.setOnClickListener {
      //点击朋友
    }
    viewBinding.avatar.setOnClickListener(clickToLogin)
    viewBinding.tvUsername.setOnClickListener(clickToLogin)
    viewBinding.tvEmail.setOnClickListener(clickToLogin)
    viewBinding.ivMore.setOnClickListener(clickToLogin)
  }


  override fun onResume() {
    super.onResume()
    if (!isLogin) {
      viewBinding.avatar.load(R.drawable.ic_default_avatar)
      viewBinding.tvUsername.text = "未登录"
      viewBinding.tvEmail.text = "点击登录"
    } else {
      viewModel.user.observe(viewLifecycleOwner) {
        //切换到主线程
        lifecycleScope.launch(Dispatchers.Main) {
          it?.let {
            viewBinding.avatar.load(it.icon) {
              transformations(CircleCropTransformation())
              placeholder(R.drawable.ic_default_avatar)
              error(R.drawable.ic_default_avatar)
            }
            viewBinding.tvUsername.text = it.nickname
            viewBinding.tvEmail.text = if (TextUtils.isEmpty(it.email))
              "暂无邮箱信息" else it.email
          }
        }
      }
    }
  }


  /**
   * 点击到达登录页面
   */
  private val clickToLogin = View.OnClickListener {
    enterNextPage()
  }

  /**
   * 进入其他页面
   */
  private fun enterNextPage() {
    if (!isLogin) {
      startActivity(Intent(requireContext(), LoginActivity::class.java))
    } else {
      startActivity(Intent(requireContext(), UserInfoActivity::class.java))
    }
  }

}