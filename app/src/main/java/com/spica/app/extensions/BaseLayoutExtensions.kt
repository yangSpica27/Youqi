@file:Suppress("unused")

package com.spica.app.extensions

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setMargins
import com.spica.app.base.BaseLayout
import com.spica.app.base.BaseLayout.Companion.MATCH_PARENT
import com.spica.app.base.BaseLayout.Companion.WRAP_CONTENT
import com.spica.app.tools.AdaptScreenUtils

interface BaseLayoutExtensions {

  companion object {
    const val INVALID_VIEW_SIZE = -3
  }

  fun getResources(): Resources

  val View.measureWidthWithMargins
    get() = measuredWidth + leftMargin + rightMargin

  val View.measureHeightWithMargins
    get() = measuredHeight + topMargin + bottomMargin

  val View.leftMargin: Int
    get() = (layoutParams as? BaseLayout.LayoutParams)?.leftMargin ?: 0

  val View.topMargin: Int
    get() = (layoutParams as? BaseLayout.LayoutParams)?.topMargin ?: 0

  val View.rightMargin: Int
    get() = (layoutParams as? BaseLayout.LayoutParams)?.rightMargin ?: 0

  val View.bottomMargin: Int
    get() = (layoutParams as? BaseLayout.LayoutParams)?.bottomMargin ?: 0


  fun Int.toExactlyMeasureSpec(): Int {
    return View.MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.EXACTLY)
  }

  fun Int.toAtMostMeasureSpec(): Int {
    return View.MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.AT_MOST)
  }

  fun View.defaultWidthMeasureSpec(parentView: ViewGroup): Int = when (layoutParams.width) {
    MATCH_PARENT -> (parentView.measuredWidth - paddingStart - paddingEnd).toExactlyMeasureSpec()
    WRAP_CONTENT -> (parentView.measuredWidth - paddingStart - paddingEnd).toAtMostMeasureSpec()
    0 -> throw IllegalAccessException("Need special treatment for $this")
    else -> layoutParams.width.toExactlyMeasureSpec()
  }

  fun View.defaultHeightMeasureSpec(parentView: ViewGroup): Int = when (layoutParams.height) {
    MATCH_PARENT -> (parentView.measuredHeight - paddingTop - paddingBottom).toExactlyMeasureSpec()
    WRAP_CONTENT -> (parentView.measuredHeight - paddingTop - paddingBottom).toAtMostMeasureSpec()
    0 -> throw IllegalAccessException("Need special treatment for $this")
    else -> layoutParams.height.toExactlyMeasureSpec()
  }

  fun View.measureExactly(width: Int, height: Int) {
    measure(width.toExactlyMeasureSpec(), height.toExactlyMeasureSpec())
  }

  fun View.setPadding(padding: Int) {
    setPadding(padding, padding, padding, padding)
  }

  fun TextView.setTextSizePx(px: Float) {
    setTextSize(TypedValue.COMPLEX_UNIT_PX, px)
  }

  fun View.setViewSize(size: Int) {
    setViewSize(size, size)
  }

  fun View.setViewSize(width: Int = INVALID_VIEW_SIZE, height: Int = INVALID_VIEW_SIZE) {
    val mLayoutParams = layoutParams
    if (width != INVALID_VIEW_SIZE) mLayoutParams.width = width
    if (height != INVALID_VIEW_SIZE) mLayoutParams.height = height
    layoutParams = mLayoutParams
  }

  fun View.setMargins(size: Int) {
    (layoutParams as? BaseLayout.LayoutParams)?.setMargins(size)
  }

  fun View.setMargins(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    (layoutParams as? BaseLayout.LayoutParams)?.setMargins(left, top, right, bottom)
  }

  fun Array<View>.setMargins(size: Int) {
    forEach { it.setMargins(size) }
  }

  fun Array<View>.setMargins(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    forEach { it.setMargins(left, top, right, bottom) }
  }
}