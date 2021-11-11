package com.spica.app.ui.webview

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView


class ObservableWebView(context: Context, attrs: AttributeSet?) : WebView(context, attrs) {




  private var onScrollChangedListener: OnScrollChangedListener? = null


  override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
    super.onScrollChanged(l, t, oldl, oldt)
    if (onScrollChangedListener != null) {
      onScrollChangedListener!!.onScrollChanged(this, l, t, oldl, oldt)
    }
  }


  fun setOnScrollChangedListener(onScrollChangedListener: OnScrollChangedListener?) {
    this.onScrollChangedListener = onScrollChangedListener
  }


  fun getOnScrollChangedListener(): OnScrollChangedListener? {
    return onScrollChangedListener
  }


  interface OnScrollChangedListener {
    fun onScrollChanged(v: WebView?, x: Int, y: Int, oldX: Int, oldY: Int)
  }

}