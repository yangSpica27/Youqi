package cn.tagux.calendar.initializer

import android.content.Context
import androidx.startup.Initializer
import cn.tagux.calendar.BuildConfig
import timber.log.Timber

/**
 * 初始化日志框架
 */
@Suppress("unused")
class TimberInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
      Timber.d("TimberInitializer is initialized.")
    }
  }

  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}