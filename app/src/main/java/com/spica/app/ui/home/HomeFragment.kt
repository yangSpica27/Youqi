package com.spica.app.ui.home

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.google.android.material.transition.Hold
import com.spica.app.R
import com.spica.app.base.BindingFragment
import com.spica.app.databinding.LayoutListBinding
import com.spica.app.model.banner.BannerData
import com.spica.app.ui.webview.WebActivity
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * 主页
 */
@AndroidEntryPoint
class HomeFragment : BindingFragment<LayoutListBinding>() {
  /**
   * viewModel
   */
  private val viewModel: ArticleViewModel by viewModels()


  /**
   * banner数据
   */
  private val banners = mutableListOf<BannerData?>()


  /**
   * 列表适配器
   */
  private val listAdapter by lazy {
    ArticleAdapter(false)
  }

  /**
   * Banner
   */
  private val bannerView by lazy {
    com.youth.banner.Banner<BannerData, BannerImageAdapter<BannerData>>(requireContext())
  }

  /**
   * banner的适配器
   */
  private val bannerAdapter = object : BannerImageAdapter<BannerData>(banners) {
    override fun onBindView(holder: BannerImageHolder, data: BannerData, position: Int, size: Int) {
      holder.imageView.load(data.imagePath)
    }
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    exitTransition = Hold()
  }

  /**
   * 绑定ViewBinding
   */
  override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?):
      LayoutListBinding = LayoutListBinding.inflate(inflater)

  @SuppressLint("NotifyDataSetChanged")
  override fun init() {

    OverScrollDecoratorHelper.setUpOverScroll(
      viewBinding.recyclerView,
      OverScrollDecoratorHelper.ORIENTATION_VERTICAL
    )

    addDivider()

    // 初始化BannerView
    bannerView.run {
      layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        168.dp
      )
    }
    // 添加到RecyclerView的header
    listAdapter.addHeaderView(bannerView)
    // 触发加载更多
    listAdapter.loadMoreModule.isEnableLoadMore = true
    listAdapter.loadMoreModule.setOnLoadMoreListener {
      loadData(false)
    }
    //绑定recyclerview的适配器
    viewBinding.recyclerView.adapter = listAdapter

    //banner绑定适配器
    bannerView
      .addBannerLifecycleObserver(viewLifecycleOwner)
      .indicator = CircleIndicator(requireContext())
    bannerView.setAdapter(bannerAdapter)

    //viewModel开始发起请求
    viewModel.getBanner(
      onStart = {
        //可以再请求开始的时候做一些操作，比如弹框
      },
      onComplete = {
        //可以再请求结束的时候做一些操作，比如关闭弹框
      },
      onError = {
        //过程之中发生错误的回调
        it?.let {
          showToast(it)
        }
      })
    addObserver()
    viewBinding.layoutSwipe.setOnRefreshListener { loadData(true) }
  }

  /**
   * 架线
   */
 private fun addDivider(){
    //recyclerview划线

    requireContext().dividerBuilder()
      .colorRes(R.color.line_divider)
      .size(1, TypedValue.COMPLEX_UNIT_DIP)
      .build()
      .addTo(viewBinding.recyclerView)
  }

  /**
   * 数据观察
   */
  private fun addObserver() {
    listAdapter.setOnItemClickListener { _, view, position ->
      kotlin.run {
        val intent = WebActivity.newIntent(
          requireContext(),
          listAdapter.data[position].title,
          listAdapter.data[position].link
        )



        val options = ActivityOptions.makeSceneTransitionAnimation(
          requireActivity(),
          view,
          "shared_element_container" // The transition name to be matched in Activity B.
        )
        startActivity(intent,options.toBundle())
      }

    }
    lifecycleScope.launch {
      viewModel.bannerFLow.collect {
        //处理收集的数据
        lifecycleScope.launch(Dispatchers.Main) {
          banners.clear()
          banners.addAll(it)
          bannerAdapter.notifyDataSetChanged()
        }
      }
    }

    lifecycleScope.launch {
      viewModel.articleFLow.collect {
        lifecycleScope.launch(Dispatchers.Main) {
          it.let {
            listAdapter.loadMoreModule.isEnableLoadMore = !it.over
            if (viewBinding.layoutSwipe.isRefreshing) {
              viewBinding.layoutSwipe.isRefreshing = false
            }
            listAdapter.addData(it.datas)
            listAdapter.loadMoreModule.loadMoreComplete()
          }
        }
      }
    }



    lifecycleScope.launch(Dispatchers.IO) {
      viewModel.errorMessage.collect {
        if (!TextUtils.isEmpty(it)) {
          showToast(it)
        }
      }
    }

  }


  private fun loadData(isRefresh: Boolean) {
    if (isRefresh) {
      listAdapter.setNewInstance(mutableListOf())
    }
    viewModel.loadMoreHomeArticle(isRefresh)
  }


}