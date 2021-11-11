package com.spica.app.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spica.app.ui.homecontainer.HomeContainerFragment
import com.spica.app.ui.mine.MineFragment
import com.spica.app.ui.projectcontainer.ProjectContainerFragment
import com.spica.app.ui.wxarticlecontainer.WxArticleContainerFragment

/**
 * 主页的pager适配器
 */
class MainPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
  FragmentStateAdapter(fragmentManager, lifecycle) {


  private val fragmentsCreators: Map<Int, () -> Fragment> = mapOf(
    HOME to { HomeContainerFragment() },
    BLOG to { WxArticleContainerFragment() },
    PROJECT to { ProjectContainerFragment() },
    ME to { MineFragment() }
  )

  override fun getItemCount(): Int = fragmentsCreators.size


  override fun createFragment(position: Int): Fragment {
    return fragmentsCreators[position]?.invoke() ?: HomeContainerFragment()
  }


  companion object {
    const val HOME = 0 //首页
    const val BLOG = 1  //公众号
    const val PROJECT = 2 //项目集
    const val ME = 3 //我的
  }
}