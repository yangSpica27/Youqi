package com.spica.app.ui.comment

import androidx.recyclerview.widget.DiffUtil
import com.spica.app.model.comment.CommentItem

class DiffCommentCallBack : DiffUtil.ItemCallback<CommentItem>() {

    override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
        return oldItem.content == newItem.content
    }

}