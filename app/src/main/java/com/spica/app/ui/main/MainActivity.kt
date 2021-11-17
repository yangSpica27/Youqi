package com.spica.app.ui.main

import android.animation.ValueAnimator
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
import com.spica.app.tools.SpicaColorEvaluator
import com.spica.app.tools.ViewPagerLayoutManager
import com.spica.app.ui.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


/**
 * 主页面
 */
@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {


    private var gradientColor = listOf(
        intArrayOf(
            Color.parseColor("#64B5F6"),
            Color.parseColor("#4FC3F7")
        ),
        intArrayOf(
            Color.parseColor("#81C784"),
            Color.parseColor("#AED581")
        ), intArrayOf(
            Color.parseColor("#FFB74D"),
            Color.parseColor("#FFD54F")
        ),
        intArrayOf(
            Color.parseColor("#4FC3F7"),
            Color.parseColor("#4DD0E1")
        ),
        intArrayOf(
            Color.parseColor("#DCE775"),
            Color.parseColor("#FFF176")
        ),
        intArrayOf(
            Color.parseColor("#7986CB"),
            Color.parseColor("#64B5F6")
        ),
        intArrayOf(
            Color.parseColor("#4DB6AC"),
            Color.parseColor("#4FC3F7")
        )
    )


    private var currentColor = intArrayOf(
        Color.parseColor("#4DB6AC"),
        Color.parseColor("#4FC3F7")
    )


    private val spicaColorEvaluator: SpicaColorEvaluator = SpicaColorEvaluator()

    private val colorAnim = ValueAnimator.ofFloat(0F, 1F)


    // 背景
    private var bg = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        gradientColor[0]
    )

    // 卡片的布局管理
    private val cardLayoutManager =
        ViewPagerLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            true
        )


    private val sentenceAdapter: SentenceAdapter by lazy {
        SentenceAdapter().apply {
            addData(listOf(1, 2, 3, 4, 5, 6, 7))
        }
    }

    override fun initializer() {

        //透明状态栏
        immersionBar() {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        //布局整体下沉
        viewBinding.root.setPadding(
            viewBinding.root.paddingLeft,
            statusBarHeight + 24.dp,
            viewBinding.root.paddingRight,
            viewBinding.root.paddingBottom
        )

        //设置默认背景
        viewBinding.root.background = bg

        //监听以实现加变色转化动画
        cardLayoutManager.setOnViewPagerListener(object :
            ViewPagerLayoutManager.OnViewPagerListener {

            override fun onInitComplete() = Unit

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                viewBinding.tvCalendar.setText(
                    "${Calendar.getInstance().get(Calendar.MONTH) + 1}",
                    "${Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - (position)}"
                )
                if (colorAnim.isRunning) {
                    colorAnim.cancel()
                }
                val nextColor = gradientColor[position]
                colorAnim.removeAllUpdateListeners()
                colorAnim.addUpdateListener {

                    val colorTop = spicaColorEvaluator.evaluate(it.animatedValue as Float, currentColor[0], nextColor[0])
                    val colorBottom = spicaColorEvaluator.evaluate(it.animatedValue as Float, currentColor[1], nextColor[1])

                    currentColor[0] = colorTop as Int
                    currentColor[1] = colorBottom as Int

                    val bg = GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        currentColor
                    )

                    viewBinding.root.background = bg

                }
                colorAnim.start()
            }

            override fun onPageRelease(isNext: Boolean, position: Int) = Unit

        })

        //布局管理器
        viewBinding.rvCard.layoutManager =
            cardLayoutManager

        //卡片的适配器
        viewBinding.rvCard.adapter = sentenceAdapter

        //点击头像
        viewBinding.ivAvatar.setOnClickListener {

            val intent = Intent(this, SettingActivity::class.java)

            startActivity(intent)

        }


    }

    override fun setupViewBinding(inflater: LayoutInflater):
            ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}
