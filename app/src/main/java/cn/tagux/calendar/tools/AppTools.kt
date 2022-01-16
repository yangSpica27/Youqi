package cn.tagux.calendar.tools

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.coordinatorlayout.widget.ViewGroupUtils
import cn.tagux.calendar.BuildConfig
import cn.tagux.calendar.R
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


// 扩大剑姬区域
@SuppressLint("RestrictedApi")
fun View.expand(dx: Int, dy: Int) {
  // 将刚才定义代理类放到方法内部，调用方不需要了解这些细节
  class MultiTouchDelegate(bound: Rect? = null, delegateView: View) : TouchDelegate(bound, delegateView) {
    val delegateViewMap = mutableMapOf<View, Rect>()
    private var delegateView: View? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {
      val x = event.x.toInt()
      val y = event.y.toInt()
      var handled = false
      when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
          delegateView = findDelegateViewUnder(x, y)
        }
        MotionEvent.ACTION_CANCEL -> {
          delegateView = null
        }
      }
      delegateView?.let {
        event.setLocation(it.width / 2f, it.height / 2f)
        handled = it.dispatchTouchEvent(event)
      }
      return handled
    }

    private fun findDelegateViewUnder(x: Int, y: Int): View? {
      delegateViewMap.forEach { entry -> if (entry.value.contains(x, y)) return entry.key }
      return null
    }
  }

  // 获取当前控件的父控件
  val parentView = parent as? ViewGroup
  // 若父控件不是 ViewGroup, 则直接返回
  parentView ?: return

  // 若父控件未设置触摸代理，则构建 MultiTouchDelegate 并设置给它
  if (parentView.touchDelegate == null) parentView.touchDelegate = MultiTouchDelegate(delegateView = this)
  post {
    val rect = Rect()
    // 获取子控件在父控件中的区域
    ViewGroupUtils.getDescendantRect(parentView, this, rect)
    // 将响应区域扩大
    rect.inset(- dx, - dy)
    // 将子控件作为代理控件添加到 MultiTouchDelegate 中
    (parentView.touchDelegate as? MultiTouchDelegate)?.delegateViewMap?.put(this, rect)
  }
}
