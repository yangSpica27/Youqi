package com.spica.app.ui.wxarticlecontainer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.FragmentHomeContainerBinding
import com.spica.app.ui.wxarticlelist.WxArticleListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * 微信公众号
 */
@AndroidEntryPoint
class WxArticleContainerFragment : BindingFragment<FragmentHomeContainerBinding>() {


  private val viewModel by viewModels<WxArticleViewModel>()


  private lateinit var pagerAdapter: ContainerPagerAdapter


  private val fragmentsCreators: MutableList<FragmentCreator> = mutableListOf()


  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      FragmentHomeContainerBinding = FragmentHomeContainerBinding.inflate(inflater, container, false)


  override fun init() {
    lifecycleScope.launch {
      viewModel.chapterFlow.collect {
        it.forEach { chapter ->
          fragmentsCreators.add(FragmentCreator(chapter.name)
          { WxArticleListFragment.newInstance(chapter.id) })
        }

        val fragments: List<Fragment> = fragmentsCreators.map { item -> item.fragment.invoke() }

        pagerAdapter = ContainerPagerAdapter(this@WxArticleContainerFragment, fragments)

        lifecycleScope.launch(Dispatchers.Main) {
          viewBinding.viewPager.adapter = pagerAdapter
          TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            tab.text = fragmentsCreators[position].title
          }.attach()
        }
      }
    }

  }


  private class FragmentCreator(
    val title: String,
    val fragment: () -> Fragment
  )


}