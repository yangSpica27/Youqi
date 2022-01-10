package com.spica.app.ui.main

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import com.spica.app.base.BindingActivity
import com.spica.app.databinding.ActivityMainBinding
import com.spica.app.extensions.dp
import com.spica.app.extensions.hide
import com.spica.app.extensions.show
import com.spica.app.model.YData
import com.spica.app.tools.SpicaColorEvaluator
import com.spica.app.tools.ViewPagerLayoutManager
import com.spica.app.tools.calendar.DateFormatter
import com.spica.app.tools.calendar.LunarCalendar
import com.spica.app.tools.doOnMainThreadIdle
import com.spica.app.tools.getDate
import com.spica.app.ui.comment.CommentActivity
import com.spica.app.ui.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


/**
 * 主页面
 */
@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {


    private val viewModel by viewModels<MainViewModel>()

    private val gradientColor by lazy {
        listOf(
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
    }

    private val items: MutableList<YData> = mutableListOf()

    private var currentColor = intArrayOf(
        Color.parseColor("#4DB6AC"),
        Color.parseColor("#4FC3F7")
    )

    //颜色估值器
    private val spicaColorEvaluator: SpicaColorEvaluator = SpicaColorEvaluator()

    //颜色变化动画
    private val colorAnim = ValueAnimator.ofFloat(0F, 1F).apply {
        duration = 500
    }

    // 背景
    private var bg =
        GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            gradientColor[0]
        )

    // 卡片的布局管理
    private val cardLayoutManager =
        ViewPagerLayoutManager(this, RecyclerView.HORIZONTAL, true)

    private val sentenceAdapter: SentenceAdapter by lazy {
        SentenceAdapter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun initializer() {

        lifecycleScope.launch(Dispatchers.Main) {

            viewModel.errorMessage.collectLatest {
                // 错误信息
                withContext(Dispatchers.Main) {
                    it?.let {
                        if (it.isNotEmpty())
                            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.isLoading.collectLatest {
                // 是否正在加载
                withContext(Dispatchers.Main) {
                    if (it) {
                        viewBinding.pbLoading.show()
                        viewBinding.rvCard.visibility = View.INVISIBLE
                    } else {
                        viewBinding.pbLoading.hide()
                        viewBinding.rvCard.show()
                    }
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.dateList.collectLatest {
                it?.let {
                    items.clear()
                    items.addAll(it.data)
                    sentenceAdapter.setNewInstance(items)
                    initHeader()
                    // cpu空闲时候再执行
                    doOnMainThreadIdle(
                        {
                            initRecyclerview()
                        }
                    )
                }
            }
        }


        lifecycleScope.launch {
            viewModel.dateList()
        }

        val lunarCalendar = LunarCalendar()

        val formatter = DateFormatter(resources)

        viewBinding.tv24.text = "${
            formatter
                .getSolarTermName(
                    lunarCalendar
                        .getGregorianDate(Calendar.MONTH) * 2
                )
        } ${formatter.getMonthName(lunarCalendar)}" +
                "${formatter.getDayName(lunarCalendar)}"


        //透明状态栏
        immersionBar {
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

        //点击头像
        viewBinding.ivAvatar.setOnClickListener {

            val intent = Intent(this, SettingActivity::class.java)

            startActivity(intent)

        }

        // 点击评论
        viewBinding.tvComment.setOnClickListener {
            startActivity(Intent(this, CommentActivity::class.java))
        }


        // 点击回到今天
        viewBinding.tvBackToday.setOnClickListener {
            viewBinding.rvCard.smoothScrollToPosition(0)
        }

    }

    private suspend fun initHeader() = withContext(Dispatchers.Main) {
        val currentDate = items[0].date.getDate()
        viewBinding.tv72.text = items[0].wuhou
        viewBinding.tv24.text = items[0].lunar
        viewBinding.tvCalendar.setText(
            mouth = (currentDate.month + 1).toString(),
            day = currentDate.date.toString()
        )
    }


    private fun initRecyclerview() {
        //监听以实现加变色转化动画
        sentenceAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
        cardLayoutManager.setOnViewPagerListener(object :
            ViewPagerLayoutManager.OnViewPagerListener {

            override fun onInitComplete() =Unit

            override fun onPageSelected(position: Int, isBottom: Boolean) {

                if (position == 0) {
                    // 根据滑动位置显示和隐藏"回到今天"
                    viewBinding.tvBackToday.hide()
                } else {
                    viewBinding.tvBackToday.show()
                }

                val currentDate = items[position].date.getDate()
                viewBinding.tv72.text = items[position].wuhou
                viewBinding.tv24.text = items[position].lunar
                viewBinding.tvCalendar.setText(
                    mouth = (currentDate.month + 1).toString(),
                    day = currentDate.date.toString()
                )


                //停止当前动画
                if (colorAnim.isRunning) {
                    colorAnim.cancel()
                }

                //获取下个动画的颜色对象
                val nextColor = gradientColor[position]

                //清除动画监听
                colorAnim.removeAllUpdateListeners()

                //更新动画实现
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
    }

    override fun setupViewBinding(inflater: LayoutInflater):
            ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}
