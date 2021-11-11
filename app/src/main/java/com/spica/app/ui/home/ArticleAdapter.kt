package com.spica.app.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Html.fromHtml
import android.text.TextUtils
import coil.load
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.spica.app.R
import com.spica.app.databinding.ItemArticleBinding
import com.spica.app.databinding.ItemArticleImgBinding
import com.spica.app.extensions.hide
import com.spica.app.model.article.ArticleItem


@Suppress("unused")
class ArticleAdapter constructor(private val showStar: Boolean) : LoadMoreModule,
  BaseMultiItemQuickAdapter<ArticleItem, BaseViewHolder>() {


  init {
    // 绑定 layout 对应的 type
    addItemType(ITEM_TEXT, R.layout.item_article)
    addItemType(ITEM_IMG, R.layout.item_article_img)
  }

  companion object {
    const val ITEM_TEXT = 0//纯文本
    const val ITEM_IMG = 1//带图
  }

  @SuppressLint("SetTextI18n")
  @Suppress("ObsoleteSdkInt")
  override fun convert(holder: BaseViewHolder, item: ArticleItem) {

    if (holder.itemViewType == ITEM_TEXT) {
      val binding = ItemArticleBinding.bind(holder.itemView)
      binding.articleTime.text = item.niceShareDate
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        binding.articleTitle.text = fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
      } else {
        binding.articleTitle.text = fromHtml(item.title)
      }
      if (TextUtils.isEmpty(item.desc)){
        binding.tvDesc.hide()
      }else{
        binding.tvDesc.text = item.desc
      }

      binding.articleTag.text = item.chapterName + "·" + item.superChapterName
      if (item.author.isBlank()) {
        binding.articleAuthor.text = item.shareUser
      } else {
        binding.articleAuthor.text = item.author
      }
    }

    if (holder.itemViewType == ITEM_IMG) {
      val binding = ItemArticleImgBinding.bind(holder.itemView)
      if (item.author.isBlank()) {
        binding.tvAuthor.text = item.shareUser
      } else {
        binding.tvAuthor.text = item.author
      }
      binding.tvDesc.text = item.desc
      binding.tvTime.text = item.niceShareDate
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        binding.tvTitle.text = fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
      } else {
        binding.tvTitle.text = fromHtml(item.title)
      }
      binding.ivPic.load(item.envelopePic)
      if (item.chapterName == "未分类")
        binding.chipGroup.hide()
      binding.chip.text = item.chapterName
    }

  }
}