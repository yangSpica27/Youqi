package cn.tagux.calendar.ui.comment

import androidx.recyclerview.widget.DiffUtil
import cn.tagux.calendar.model.comment.CommentItem

class DiffCommentCallBack : DiffUtil.ItemCallback<CommentItem>() {

    override fun areItemsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentItem, newItem: CommentItem): Boolean {
        return oldItem.content == newItem.content
    }
}
