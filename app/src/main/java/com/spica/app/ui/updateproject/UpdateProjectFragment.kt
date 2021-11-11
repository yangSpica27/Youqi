package com.spica.app.ui.updateproject

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.spica.app.R
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.LayoutListBinding
import com.spica.app.ui.home.ArticleAdapter
import com.spica.app.ui.home.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * 最新项目列表
 */
@AndroidEntryPoint
class UpdateProjectFragment : BindingFragment<LayoutListBinding>() {


  private val listAdapter by lazy {
    ArticleAdapter(false)
  }

  private val viewModel by viewModels<ArticleViewModel>()


  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      LayoutListBinding = LayoutListBinding.inflate(layoutInflater, container, false)

  override fun init() {
    addDivider()
    viewBinding.layoutSwipe.isEnabled = false
    viewBinding.recyclerView.adapter = listAdapter
    listAdapter.loadMoreModule.setOnLoadMoreListener {
      viewModel.loadMoreProject(false)
    }
    lifecycleScope.launch {
      viewModel.updateProjectsFlow.collect {
        listAdapter.addData(it.datas)
        listAdapter.loadMoreModule.loadMoreComplete()
      }
    }
  }


  /**
   * 架线
   */
  private fun addDivider() {
    //recyclerview划线
    requireContext().dividerBuilder()
      .colorRes(R.color.line_divider)
      .size(1, TypedValue.COMPLEX_UNIT_DIP)
      .build()
      .addTo(viewBinding.recyclerView)
  }


}