package cn.tagux.calendar.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import cn.tagux.calendar.R
import cn.tagux.calendar.databinding.ItemDateArticleBinding
import cn.tagux.calendar.databinding.ItemDateNormalBinding
import cn.tagux.calendar.databinding.ItemDatePicBinding
import cn.tagux.calendar.extensions.dp
import cn.tagux.calendar.extensions.hide
import cn.tagux.calendar.extensions.show
import cn.tagux.calendar.model.date.ARTICLE
import cn.tagux.calendar.model.date.AUDIO
import cn.tagux.calendar.model.date.NORMAL
import cn.tagux.calendar.model.date.PIC
import cn.tagux.calendar.model.date.YData
import cn.tagux.calendar.tools.doOnMainThreadIdle
import cn.tagux.calendar.ui.detail.DetailActivity
import coil.load
import coil.transform.RoundedCornersTransformation
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
/**
 * 每日句子的适配器
 */
class SentenceAdapter(val activity: Activity) :
    BaseMultiItemQuickAdapter<YData, BaseViewHolder>() {

    init {
        // 添加普通类型
        addItemType(NORMAL, R.layout.item_date_normal)
        // 音频类型
        addItemType(AUDIO, R.layout.item_date_normal)
        // 文章类型
        addItemType(ARTICLE, R.layout.item_date_article)
        // 图片类型
        addItemType(PIC, R.layout.item_date_pic)
    }

    @SuppressLint("SetTextI18n")
    override fun convert(holder: BaseViewHolder, item: YData) {

        when (holder.itemViewType) {

            NORMAL -> {
                val itemBinding = ItemDateNormalBinding.bind(holder.itemView)
                itemBinding.tvTitle.text = item.content.title
                itemBinding.tvFrom.text = "「${item.content.personSim}」"
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
                } else {
                    itemBinding.btnDetail.hide()
                }
            }

            AUDIO -> {
                val itemBinding = ItemDateNormalBinding.bind(holder.itemView)
                itemBinding.tvTitle.text = item.content.title
                itemBinding.tvFrom.text = "「${item.content.personSim}」"
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
                } else {
                    itemBinding.btnDetail.hide()
                }
            }

            PIC -> {
                val itemBinding = ItemDatePicBinding.bind(holder.itemView)
                itemBinding.tvFrom.text = item.content.title
                doOnMainThreadIdle({
                    itemBinding.ivPic.load(item.content.picture) {
                        transformations(RoundedCornersTransformation(6.dp.toFloat()))
                    }
                })
            }

            ARTICLE -> {
                val itemBinding = ItemDateArticleBinding.bind(holder.itemView)
                itemBinding.tvTitle.text = item.content.title
                itemBinding.tvFrom.text = "「${item.content.personSim}」"
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
                } else {
                    itemBinding.btnDetail.hide()
                }
            }
        }
    }
}
