package com.spica.app.ui.main

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import coil.load
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.spica.app.R
import com.spica.app.databinding.ItemDateArticleBinding
import com.spica.app.databinding.ItemDateNormalBinding
import com.spica.app.databinding.ItemDatePicBinding
import com.spica.app.extensions.hide
import com.spica.app.extensions.show
import com.spica.app.model.date.*
import com.spica.app.tools.doOnMainThreadIdle
import com.spica.app.ui.detail.DetailActivity

class SentenceAdapter(val activity: Activity) :
    BaseMultiItemQuickAdapter<YData, BaseViewHolder>() {


    init {
        addItemType(NORMAL,R.layout.item_date_normal)
        addItemType(AUDIO,R.layout.item_date_normal)
        addItemType(ARTICLE,R.layout.item_date_article)
        addItemType(PIC,R.layout.item_date_pic)
    }


    override fun convert(holder: BaseViewHolder, item: YData) {


        when(holder.itemViewType){

            NORMAL ->{
                val itemBinding = ItemDateNormalBinding.bind(holder.itemView)
                itemBinding.tvTitle.text = item.content.title
                itemBinding.tvFrom.text = item.content.personSim
                if (item.content.isArticle == 1) {
                    itemBinding.btnDetail.show()
                    itemBinding.cdContainer.setOnClickListener {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("cid", item.content.id)
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

            AUDIO ->{
                val itemBinding = ItemDateNormalBinding.bind(holder.itemView)
                itemBinding.tvTitle.text = item.content.title
                itemBinding.tvFrom.text = item.content.personSim
                if (item.content.isArticle == 1) {
                    itemBinding.btnDetail.show()
                    itemBinding.cdContainer.setOnClickListener {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("cid", item.content.id)
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

            PIC ->{
                val itemBinding = ItemDatePicBinding.bind(holder.itemView)
                itemBinding.tvFrom.text = item.content.title
                doOnMainThreadIdle({
                    itemBinding.ivPic.load(item.content.picture){
                    }
                })

            }

            ARTICLE ->{
                val itemBinding = ItemDateArticleBinding.bind(holder.itemView)
                itemBinding.tvTitle.text = item.content.title
                itemBinding.tvFrom.text = item.content.personSim
                if (item.content.isArticle == 1) {
                    itemBinding.btnDetail.show()
                    itemBinding.cdContainer.setOnClickListener {
                        val intent = Intent(context, DetailActivity::class.java)
                        intent.putExtra("cid", item.content.id)
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


    }


}
