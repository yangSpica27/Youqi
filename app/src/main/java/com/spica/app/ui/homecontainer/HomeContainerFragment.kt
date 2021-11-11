package com.spica.app.ui.homecontainer

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.FragmentHomeContainerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeContainerFragment : BindingFragment<FragmentHomeContainerBinding>() {


  private val titleList = arrayOf("首页", "广场", "最新项目", "体系", "导航")

  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      FragmentHomeContainerBinding = FragmentHomeContainerBinding
    .inflate(inflater, container, false)

  override fun init() {
    viewBinding.viewPager.adapter = HomePagerAdapter(this)
    TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
      tab.text = titleList[position]
    }.attach()
  }


}