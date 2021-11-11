package com.spica.app.ui.wxarticlelist

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.spica.app.R
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.LayoutListBinding
import com.spica.app.ui.home.ArticleAdapter
import com.spica.app.ui.wxarticlecontainer.WxArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private const val KEY_ID = "ID"

@AndroidEntryPoint
class WxArticleListFragment : BindingFragment<LayoutListBinding>() {

  private val viewModel: WxArticleViewModel by viewModels()

  private val listAdapter by lazy {
    ArticleAdapter(false)
  }

  companion object {
    fun newInstance(id: Int): Fragment {
      val fragment = WxArticleListFragment()
      val bundle = Bundle()
      bundle.putInt(KEY_ID, id)
      fragment.arguments = bundle
      return fragment
    }
  }


  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      LayoutListBinding = LayoutListBinding.inflate(inflater, container, false)

  override fun init() {
    viewBinding.layoutSwipe.isEnabled = false
    addDivider()
    viewBinding.recyclerView.adapter = listAdapter
    val id = arguments?.getInt(KEY_ID) ?: 0
    loadMore(id)
    listAdapter.loadMoreModule.setOnLoadMoreListener {
      loadMore(id)
    }
    lifecycleScope.launch {
      viewModel.articleFlow.collectLatest {
        listAdapter.addData(it)
        listAdapter.loadMoreModule.loadMoreComplete()

        if (it.isEmpty()) listAdapter.loadMoreModule.loadMoreEnd(true)
      }
    }

  }


  /**
   * 加分割线
   */
  private fun addDivider() {
    //recyclerview划线
    requireContext().dividerBuilder()
      .colorRes(R.color.line_divider)
      .size(1, TypedValue.COMPLEX_UNIT_DIP)
      .build()
      .addTo(viewBinding.recyclerView)
  }


  private fun loadMore(id: Int) {
    viewModel.getArticles(id)
  }


}