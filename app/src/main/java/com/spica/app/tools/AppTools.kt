package com.spica.app.tools

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.spica.app.R


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