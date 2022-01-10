package com.spica.app.tools

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.spica.app.BuildConfig
import com.spica.app.R
import timber.log.Timber


/**
 * 获取版本号
 */
fun Context.getVersion(): String {
  return try {
    val packageInfo = packageManager.getPackageInfo(packageName, 0)
    packageInfo.versionName
  } catch (e: Exception) {
    e.printStackTrace()
    getString(R.string.fail_to_get_version_code)
  }
}

fun View.hideKeyboard() {
  val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}


fun View.showKeyboard() {
  if (requestFocus()) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
  }
}



fun doOnMainThreadIdle(action: () -> Unit, timeout: Long? = null) {
  val handler = Handler(Looper.getMainLooper())

  val idleHandler = MessageQueue.IdleHandler {
    handler.removeCallbacksAndMessages(null)
    action()
    return@IdleHandler false
  }

  fun setupIdleHandler(queue: MessageQueue) {
    if (timeout != null) {
      handler.postDelayed({
        queue.removeIdleHandler(idleHandler)
        action()
        if (BuildConfig.DEBUG) {
          Timber.d("doOnMainThreadIdle: ${timeout}ms timeout!")
        }
      }, timeout)
    }
    queue.addIdleHandler(idleHandler)
  }

  if (Looper.getMainLooper() == Looper.myLooper()) {
    setupIdleHandler(Looper.myQueue())
  } else {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      setupIdleHandler(Looper.getMainLooper().queue)
    } else {
      handler.post { setupIdleHandler(Looper.myQueue()) }
    }
  }

}