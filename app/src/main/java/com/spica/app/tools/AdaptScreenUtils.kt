@file:Suppress("unused")

package com.spica.app.tools

import android.content.res.Resources

object AdaptScreenUtils {

  @JvmStatic
  fun adaptWidth(resources: Resources, designWidth: Int): Resources {
    return resources.apply {
      val newXdpi = displayMetrics.widthPixels * 72f / designWidth
      displayMetrics.xdpi = newXdpi
    }
  }

  @JvmStatic
  fun adaptHeight(resources: Resources, designHeight: Int): Resources {
    return resources.apply {
      val newXdpi = displayMetrics.heightPixels * 72f / designHeight
      displayMetrics.xdpi = newXdpi
    }
  }

}