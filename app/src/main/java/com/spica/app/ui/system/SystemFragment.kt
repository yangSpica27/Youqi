package com.spica.app.ui.system

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.spica.app.R
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.LayoutListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import javax.inject.Inject

/**
 * 体系
 */
@AndroidEntryPoint
class SystemFragment : BindingFragment<LayoutListBinding>() {


  @Inject
  lateinit var listAdapter: SystemTypeAdapter

  private val viewModel: SystemViewModel by viewModels()

  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      LayoutListBinding = LayoutListBinding.inflate(inflater, container, false)

  override fun init() {
    addDivider()
    OverScrollDecoratorHelper.setUpOverScroll(
      viewBinding.recyclerView,
      OverScrollDecoratorHelper.ORIENTATION_VERTICAL
    )

    lifecycleScope.launch {
      viewModel.isLoading.collect {
        lifecycleScope.launch(Dispatchers.Main) {
          viewBinding.layoutSwipe.isRefreshing = it
        }
      }
    }

    viewBinding.layoutSwipe.isEnabled = false
    viewBinding.recyclerView.adapter = listAdapter
    lifecycleScope.launch {
      viewModel.systemTypeFlow.collect {
        listAdapter.addData(it)
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