package com.spica.app.ui.comment

import coil.load
import coil.transform.CircleCropTransformation
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.spica.app.R
import com.spica.app.databinding.ItemCommentBinding
import com.spica.app.model.comment.CommentItem
import com.spica.app.tools.doOnMainThreadIdle
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter : BaseQuickAdapter<CommentItem, BaseViewHolder>(R.layout.item_comment) ,LoadMoreModule {


    private val prettyTime: PrettyTime = PrettyTime()

    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    override fun convert(holder: BaseViewHolder, item: CommentItem) {

        val createAt = prettyTime.format(sdf.parse(item.createdAt))

        val itemBinding = ItemCommentBinding.bind(holder.itemView)

        itemBinding.publishedAt.text = createAt

        doOnMainThreadIdle({
            itemBinding.icon.load(item.user.portrait) {
                placeholder(R.drawable.ic_default_avatar)
                transformations(CircleCropTransformation())
                error(R.drawable.ic_default_avatar)
            }
        })

        itemBinding.categoryName.text = item.user.nickname

        itemBinding.comment.text = item.content

    }


}
