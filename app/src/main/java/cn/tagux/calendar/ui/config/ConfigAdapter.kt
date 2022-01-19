package cn.tagux.calendar.ui.config

import cn.tagux.calendar.R
import cn.tagux.calendar.databinding.ItemConfigBinding
import cn.tagux.calendar.model.ConfigItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ConfigAdapter : BaseQuickAdapter<ConfigItem, BaseViewHolder>
(R.layout.item_config) {

    override fun convert(holder: BaseViewHolder, item: ConfigItem) {
        val binding = ItemConfigBinding.bind(holder.itemView)
        binding.name.text = item.leftItem
        binding.info.text = item.rightItem
    }
}
