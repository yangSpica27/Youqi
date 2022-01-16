package cn.tagux.calendar.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebView
import cn.tagux.calendar.base.BindingActivity
import cn.tagux.calendar.databinding.ActivityDetailBinding
import cn.tagux.calendar.tools.doOnMainThreadIdle
import cn.tagux.calendar.tools.widget.WebViewPool
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransform.FADE_MODE_CROSS
import com.google.android.material.transition.platform.MaterialContainerTransform.FADE_MODE_THROUGH
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback


/**
 * 详情页面
 */
class DetailActivity : BindingActivity<ActivityDetailBinding>() {

    private var url = "https://youqi.taguxdesign.com/api/content/detail/"

    private lateinit var webView: WebView

    override fun initializer() {
        url += intent.getIntExtra("cid", 0).toString()
        doOnMainThreadIdle(
            {
                webView = WebViewPool.instance.getWebView(this)
                viewBinding.root.addView(
                    webView, ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
                webView.loadUrl(url)
            }, 500
        )

    }

    override fun onBackPressed() {
        super.onBackPressed()
        WebViewPool.instance.removeWebView(viewBinding.root, webView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        findViewById<View>(android.R.id.content).transitionName = "shared_element_container"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 400L
            containerColor = Color.WHITE
            fadeMode = FADE_MODE_THROUGH
        }

        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 450L
            containerColor = Color.WHITE
            fadeMode = FADE_MODE_CROSS
        }
        super.onCreate(savedInstanceState)
    }

    override fun setupViewBinding(inflater: LayoutInflater):
            ActivityDetailBinding = ActivityDetailBinding.inflate(inflater)






}