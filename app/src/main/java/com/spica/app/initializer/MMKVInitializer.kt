package com.spica.app.initializer

import android.content.Context
import androidx.startup.Initializer
import com.tencent.mmkv.MMKV


@Suppress("unused")
class MMKVInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    MMKV.initialize(context)
  }


  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

}