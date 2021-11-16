package com.spica.app.ui.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityMainBinding
import com.spica.app.extensions.dp
import com.spica.app.tools.ViewPagerLayoutManager
import com.spica.app.ui.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint


/**
 * 主页面
 */
@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {


    private var colors =
        intArrayOf(
            Color.parseColor("#64B5F6"),
            Color.parseColor("#4FC3F7")
        )


    private var bg = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)


    private val sentenceAdapter: SentenceAdapter by lazy {
        SentenceAdapter().apply {
            addData(listOf(1, 2, 3, 4, 5, 6, 7))
        }
    }

    override fun initializer() {

        immersionBar() {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }


        viewBinding.root.setPadding(
            viewBinding.root.paddingLeft,
            statusBarHeight + 24.dp,
            viewBinding.root.paddingRight,
            viewBinding.root.paddingBottom
        )

        viewBinding.root.background = bg

        viewBinding.rvCard.layoutManager =
            ViewPagerLayoutManager(
                this, RecyclerView.HORIZONTAL,
                false
            )



        viewBinding.rvCard.adapter = sentenceAdapter


        viewBinding.ivAvatar.setOnClickListener {

            val intent = Intent(this, SettingActivity::class.java)

            startActivity(intent)

        }


    }

    override fun setupViewBinding(inflater: LayoutInflater):
            ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}
