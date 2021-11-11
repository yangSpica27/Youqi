package com.spica.app.tools

import android.content.Context
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