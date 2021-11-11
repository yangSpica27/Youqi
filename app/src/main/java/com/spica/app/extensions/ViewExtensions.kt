@file:Suppress("unused")

package com.spica.app.extensions

import android.content.res.Resources
import android.view.View

val Float.dp: Float                 // [xxhdpi](360 -> 1080)
  get() = android.util.TypedValue.applyDimension(
    android.util.TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
  )

val Int.dp: Int
  get() = android.util.TypedValue.applyDimension(
    android.util.TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
  ).toInt()


val Float.sp: Float                 // [xxhdpi](360 -> 1080)
  get() = android.util.TypedValue.applyDimension(
    android.util.TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics
  )


val Int.sp: Int
  get() = android.util.TypedValue.applyDimension(
    android.util.TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics
  ).toInt()

fun View.show() {
  this.visibility = View.VISIBLE
}

fun View.hide() {
  this.visibility = View.GONE
}

