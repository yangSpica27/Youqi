package com.spica.app.ui.config

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.spica.app.R
import com.spica.app.databinding.ItemConfigBinding
import com.spica.app.model.ConfigItem

class ConfigAdapter : BaseQuickAdapter<ConfigItem, BaseViewHolder>
    (R.layout.item_config) {

    override fun convert(holder: BaseViewHolder, item: ConfigItem) {
        val binding = ItemConfigBinding.bind(holder.itemView)
        binding.name.text = item.leftItem
        binding.info.text = item.rightItem
    }
}