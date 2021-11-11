@file:Suppress("unused")

package com.spica.app.tools

import android.annotation.SuppressLint
import android.text.TextUtils
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*


/**
 * 返回指定pattern样的日期时间字符串。
 */
@SuppressLint("SimpleDateFormat")
fun getTimeString(dt: Date, pattern: String): String {
  return try {
    val sdf = SimpleDateFormat(pattern)
    sdf.timeZone = TimeZone.getDefault()
    sdf.format(dt)
  } catch (e: Exception) {
    e.printStackTrace()
    ""
  }
}

/**
 * 24小时制转化成12小时制
 *
 * @param strDay
 */
fun timeFormatStr(calendar: Calendar, strDay: String): String? {
  var tempStr: String
  val hour = calendar[Calendar.HOUR_OF_DAY]
  tempStr = if (hour > 11) {
    "下午 $strDay"
  } else {
    "上午 $strDay"
  }
  return tempStr
}

/**
 * 格式化当前日期为xx小时前
 */
fun prettyFormatTime(date: Date): String {
  val prettyTime = PrettyTime()
  return prettyTime.format(date)
}

/**
 * 将date转换成format格式的日期
 *
 * @param format 格式
 * @param date   日期
 * @return
 */
fun simpleDateFormat(format: String, date: Date): String? {
  var format = format
  if (TextUtils.isEmpty(format)) {
    format = "yyyy-MM-dd HH:mm:ss SSS"
  }
  return SimpleDateFormat(format).format(date)
}

/**
 * 获取当前日期时间 / 得到今天的日期
 * str yyyyMMddhhMMss 之类的
 * @return
 */
@SuppressLint("SimpleDateFormat")
fun getCurrentDateTime(format: String): String? {
  return simpleDateFormat(format, Date())
}

