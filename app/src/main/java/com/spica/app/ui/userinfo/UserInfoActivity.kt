package com.spica.app.ui.userinfo

import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.appbar.AppBarLayout
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityUserInfoBinding
import com.spica.app.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * 用户信息展示页
 */
private const val IMG_URL = "https://unsplash.it/400/800/?random"

@AndroidEntryPoint
class UserInfoActivity : BindingActivity<ActivityUserInfoBinding>() {

  private val viewModel: MainViewModel by viewModels()


  override fun initializer() {


    viewBinding.headIv.load(IMG_URL) {
      placeholder(R.drawable.ic_default_avatar)
      error(R.drawable.ic_default_avatar)
      transformations(CircleCropTransformation())
    }

    viewBinding.appBarLayout.addOnOffsetChangedListener(
      AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
        if (verticalOffset <= viewBinding.headLayout.height / 2) {
          viewBinding.collapsingToolbarLayout.title = "Spica 27"
        } else {
          viewBinding.collapsingToolbarLayout.title = " "
        }
      })

    viewModel.user.observe(this) {
      it?.let {
        lifecycleScope.launch(Dispatchers.Main) {
          viewBinding.collapsingToolbarLayout.title = it.publicName
          viewBinding.tvUsername.text = it.publicName
        }
      }
    }
  }

  override fun setupViewBinding(inflater: LayoutInflater): ActivityUserInfoBinding =
    ActivityUserInfoBinding.inflate(layoutInflater)


}