package com.spica.app.ui.navigation

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.chip.Chip
import com.spica.app.R
import com.spica.app.databinding.ItemSystemBinding
import com.spica.app.model.navigation.NavigationData
import javax.inject.Inject

class NavigationAdapter
@Inject constructor() : BaseQuickAdapter<NavigationData,
    BaseViewHolder>(R.layout.item_system) {

  override fun convert(holder: BaseViewHolder, item: NavigationData) {
    val binding: ItemSystemBinding = ItemSystemBinding.bind(holder.itemView)
    binding.tvTitle.text = item.name

    val chips: List<Chip> = item.articles.map { article ->
      Chip(binding.root.context).apply {
        text = article.title
        tag = article.link
      }
    }
    binding.chipGroup.removeAllViews()
    chips.forEach {
      binding.chipGroup.addView(it)
    }

  }

}