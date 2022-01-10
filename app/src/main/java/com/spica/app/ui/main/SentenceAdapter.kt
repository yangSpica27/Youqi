package com.spica.app.ui.main

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.spica.app.R
import com.spica.app.databinding.ItemSentenceBinding
import com.spica.app.extensions.hide
import com.spica.app.extensions.show
import com.spica.app.model.YData
import com.spica.app.ui.detail.DetailActivity

class SentenceAdapter(val activity: Activity) :
    BaseQuickAdapter<YData, BaseViewHolder>(R.layout.item_sentence) {


    override fun convert(holder: BaseViewHolder, item: YData) {
        val itemBinding = ItemSentenceBinding.bind(holder.itemView)

        itemBinding.tvTitle.text = item.YContent.title
        itemBinding.tvFrom.text = item.YContent.personSim
        if (item.YContent.isArticle == 1) {
            itemBinding.btnDetail.show()
            itemBinding.cdContainer.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("cid", item.YContent.id)
                itemBinding.cdContainer.transitionName = "shared_element_container"
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    itemBinding.cdContainer,
                    "shared_element_container"
                )
                context.startActivity(intent, options.toBundle())
            }
        }else{
            itemBinding.btnDetail.hide()
        }

    }


}
