package com.spica.app.ui.main

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.spica.app.R
import com.spica.app.databinding.ItemSentenceBinding
import com.spica.app.ui.detail.DetailActivity

class SentenceAdapter(val activity: Activity) : BaseQuickAdapter<Any, BaseViewHolder>(R.layout.item_sentence) {


    override fun convert(holder: BaseViewHolder, item: Any) {
        val itemBinding = ItemSentenceBinding.bind(holder.itemView)
        itemBinding.cdContainer.setOnClickListener {

            val intent = Intent(context, DetailActivity::class.java)

//            itemBinding.cdContainer.hide()

            itemBinding.cdContainer.transitionName="shared_element_container"
            val options = ActivityOptions.makeSceneTransitionAnimation(
                activity,
                itemBinding.cdContainer,
                "shared_element_container"
            )
            context.startActivity(intent, options.toBundle())

//            itemBinding.cdContainer.show()
        }
    }


}
