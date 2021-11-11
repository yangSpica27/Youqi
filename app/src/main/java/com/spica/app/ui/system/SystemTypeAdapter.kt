package com.spica.app.ui.system

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.chip.Chip
import com.spica.app.R
import com.spica.app.databinding.ItemSystemBinding
import com.spica.app.model.system.SystemData
import javax.inject.Inject

/**
 * @ClassName SystemTypeAdapter
 * @Description TODO
 * @Author Spica2 7
 * @Date 2021/9/8 11:00
 */
class SystemTypeAdapter
@Inject constructor() :  BaseQuickAdapter<SystemData,
    BaseViewHolder>(R.layout.item_system) {

  override fun convert(holder: BaseViewHolder, item: SystemData) {
    val binding: ItemSystemBinding = ItemSystemBinding.bind(holder.itemView)
    binding.tvTitle.text = item.name

    val chips: List<Chip> = item.children.map { systemItem ->
      Chip(binding.root.context).apply {
        text = systemItem.name
        tag = systemItem.id
      }
    }
    binding.chipGroup.removeAllViews()
    chips.forEach {
      binding.chipGroup.addView(it)
    }

  }

}