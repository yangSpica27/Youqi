package com.spica.app.initializer

import android.content.Context
import androidx.startup.Initializer

class DialogXInitializer :Initializer<Unit> {

  override fun create(context: Context) {

  }


  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}