@file:Suppress("unused")

package com.spica.app.initializer

import android.content.Context
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.request.CachePolicy
import com.spica.app.extensions.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * 初始化图片加载
 */
class CoilInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    CoroutineScope(Dispatchers.Default).launch {
      val imageLoader = ImageLoader.Builder(context)
        .crossfade(true)
        .allowRgb565(true)
        .memoryCachePolicy(CachePolicy.DISABLED)
        .diskCachePolicy(CachePolicy.DISABLED)
        .allowHardware(true)
        .bitmapPoolingEnabled(false)
        .build()
      Coil.setImageLoader(imageLoader)
    }
  }

  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}