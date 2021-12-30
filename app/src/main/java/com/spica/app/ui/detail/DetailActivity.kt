package com.spica.app.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransform.FADE_MODE_THROUGH
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityDetailBinding


/**
 * 详情页面
 */
class DetailActivity : BindingActivity<ActivityDetailBinding>() {


    override fun initializer() {


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
        }
        super.onCreate(savedInstanceState)
    }

    override fun setupViewBinding(inflater: LayoutInflater):
            ActivityDetailBinding = ActivityDetailBinding.inflate(inflater)


}