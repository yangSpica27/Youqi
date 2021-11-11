package com.spica.app.ui.main

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import androidx.appcompat.widget.AppCompatTextView
import com.spica.app.R
import com.spica.app.base.CustomLayout
import com.spica.app.base.parentView

/**
 * 主页
 */
class MainPage(context: Context) : CustomLayout(context) {

  private val centerText = AppCompatTextView(context)
    .apply {
      setTextColor(Color.BLACK)
      text = "你好，世界"
      ellipsize = TextUtils.TruncateAt.END
    }


  private val centerText2 = AppCompatTextView(context)
    .apply {
      setTextColor(Color.BLACK)
      setBackgroundResource(R.color.cardview_dark_background)
      text = "发现掘金和知乎的分享界面效果挺好的，" +
          "比自己的用的AlertDialog 和 " +
          "PopupWindow的效果好太多就像学习一下，" +
          "如图是掘金的文章分享界面"
    }


  init {
    addView(centerText)
    addView(centerText2)
  }

  override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    centerText.autoMeasure()
    centerText2.autoMeasure()
  }

  override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
    centerText.let {
      it.layout(
        x = 0,
        y = parentView.verticalCenterTop(it)
      )
    }
    centerText2.let {
      it.layout(
        x = 0,
        y = parentView.verticalCenterTop(it) + 2 * centerText.measuredHeight
      )
    }
  }


}