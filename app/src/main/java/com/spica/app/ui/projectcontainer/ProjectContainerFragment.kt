package com.spica.app.ui.projectcontainer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.FragmentHomeContainerBinding
import com.spica.app.ui.project.ProjectFragment
import com.spica.app.ui.wxarticlecontainer.ContainerPagerAdapter
import com.spica.app.ui.wxarticlelist.WxArticleListFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 项目页
 */
@AndroidEntryPoint
class ProjectContainerFragment : BindingFragment<FragmentHomeContainerBinding>() {

  private val viewModel by viewModels<ProjectViewModel>()


  private lateinit var pagerAdapter: ContainerPagerAdapter


  private val fragmentsCreators: MutableList<FragmentCreator> = mutableListOf()


  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      FragmentHomeContainerBinding = FragmentHomeContainerBinding.inflate(inflater, container, false)


  override fun init() {
    lifecycleScope.launch {
      viewModel.tabsFlow.collect {
        it.forEach { chapter ->
          fragmentsCreators.add(FragmentCreator(chapter.name)
          { ProjectFragment.newInstance(chapter.id) })
        }

        val fragments: List<Fragment> = fragmentsCreators.map { item -> item.fragment.invoke() }

        pagerAdapter = ContainerPagerAdapter(this@ProjectContainerFragment, fragments)

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