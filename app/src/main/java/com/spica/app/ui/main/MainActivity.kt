package com.spica.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.spica.app.R
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementsUseOverlay = false
    super.onCreate(savedInstanceState)
  }


  private val onItemSelectedListener by lazy {
    NavigationBarView.OnItemSelectedListener {
      when (it.itemId) {
        R.id.home -> {
          viewBinding.mainViewPager.currentItem = MainPagerAdapter.HOME
          return@OnItemSelectedListener true
        }
        R.id.blog -> {
          viewBinding.mainViewPager.currentItem = MainPagerAdapter.BLOG
          return@OnItemSelectedListener true
        }
        R.id.project -> {
          viewBinding.mainViewPager.currentItem = MainPagerAdapter.PROJECT
          return@OnItemSelectedListener true
        }
        R.id.profile -> {
          viewBinding.mainViewPager.currentItem = MainPagerAdapter.ME
          return@OnItemSelectedListener true
        }
      }
      return@OnItemSelectedListener false
    }
  }

  override fun initializer() {
    initView()
  }


  private fun initView() {
    viewBinding.mainViewPager.isUserInputEnabled = false
    viewBinding.mainViewPager.offscreenPageLimit = 4
    viewBinding.mainViewPager.adapter = MainPagerAdapter(supportFragmentManager, lifecycle)
    viewBinding.bottomNavigationBar.setOnItemSelectedListener(onItemSelectedListener)
  }


  override fun setupViewBinding(inflater: LayoutInflater):
      ActivityMainBinding =
    ActivityMainBinding.inflate(inflater)
}