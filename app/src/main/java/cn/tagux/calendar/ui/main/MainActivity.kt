package cn.tagux.calendar.ui.main

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateMargins
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import cn.tagux.calendar.base.BindingActivity
import cn.tagux.calendar.databinding.ActivityMainBinding
import cn.tagux.calendar.extensions.dp
import cn.tagux.calendar.extensions.hide
import cn.tagux.calendar.extensions.show
import cn.tagux.calendar.model.date.YData
import cn.tagux.calendar.tools.*
import cn.tagux.calendar.ui.comment.CommentActivity
import cn.tagux.calendar.ui.setting.SettingActivity
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * 主页面
 */
@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {


    // 这个页面的viewModel
    private val viewModel by viewModels<MainViewModel>()


    private val items: MutableList<YData> = mutableListOf()

    // 当前的颜色集
    private var currentColor = intArrayOf(
        Color.parseColor("#4DB6AC"),
        Color.parseColor("#4FC3F7")
    )

    // 当前的position
    private var currentPosition = 0

    // 颜色估值器
    private val spicaColorEvaluator: SpicaColorEvaluator = SpicaColorEvaluator()

    // 颜色变化动画
    private val colorAnim = ValueAnimator.ofFloat(0F, 1F)
        .apply {
            duration = 500L
        }


    // 揭幕动画的圆心x坐标
    private var startX = 0F

    // 揭幕动画的圆心y坐标
    private var startY = 0F

    // 揭幕动画最大半径数
    private var maxRadius = 0

    // 揭幕动画对象
    private var revealAnim: Animator? = null

    // 背景
    private var bg =
        GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            gradientColor[0]
        )

    // 卡片的布局管理
    private val cardLayoutManager =
        ViewPagerLayoutManager(this, RecyclerView.HORIZONTAL, true)

    // 布局的适配器
    private val sentenceAdapter: SentenceAdapter by lazy {
        SentenceAdapter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        // 设置容器动画回调
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun initializer() {

        // 监听错误信息流
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.errorMessage.collectLatest {
                // 错误信息
                withContext(Dispatchers.Main) {
                    it?.let {
                        if (it.isNotEmpty())
                            Toast.makeText(
                                this@MainActivity, it,
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            }
        }

        // 监听状态信息
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

        // 获取日期卡牌信息
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.dateList.collectLatest {
                it?.let {
                    items.clear()
                    items.addAll(it.data)
                    sentenceAdapter.setNewInstance(items)
                    initHeaderAndBottomBar()
                    // cpu空闲时候再执行
                    doOnMainThreadIdle(
                        {
                            initRecyclerview()
                        }
                    )
                }
            }
        }


        // 开始网络请求
        lifecycleScope.launch {
            viewModel.dateList()
        }


        // 点击72候
        viewBinding.tv72.setOnClickListener {
            viewBinding.ivPic.load(items[currentPosition].wuhouPicture)
            lifecycleScope.launch {

                // 避免重复计算浪费性能
                initValue()

                // 计算完成开始动画
                picAnim()

            }
        }


        viewBinding.ivPic.setOnClickListener {
            if (revealAnim?.isRunning == true) {
                // 动画未结束忽略点击事件
                return@setOnClickListener
            }
            // 反向动画
            resumePicAnim()
        }

        //透明状态栏
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
            transparentNavigationBar()
        }

        //布局整体下沉适配状态栏
        val lp = viewBinding.tv72.layoutParams as ConstraintLayout.LayoutParams
        lp.updateMargins(
            top = lp.topMargin + statusBarHeight
        )
        viewBinding.tv72.layoutParams = lp


        //设置默认背景
        viewBinding.root.background = bg

        //点击头像
        viewBinding.ivAvatar.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        // 点击评论
        viewBinding.tvComment.expand(5.dp, 5.dp)
        viewBinding.tvComment.setOnClickListener {
            startActivity(
                Intent(this, CommentActivity::class.java)
                    .apply {
                        putExtra("cid", items[currentPosition].content.id)
                    })
        }


        // 点击回到今天
        viewBinding.tvBackToday.setOnClickListener {
            viewBinding.rvCard.smoothScrollToPosition(0)
        }

    }

    /**
     * 初始化动画参数
     */
    private fun initValue() {
        if (maxRadius == 0) {
            // 最大半径为0时触发计算
            startX = viewBinding.tv72.x
            startY = viewBinding.tv72.y
            //将屏幕分成4个小矩形
            val leftTop = RectF(0F, 0F, startX + 0F, startY + 0F)
            val rightTop = RectF(
                leftTop.right, 0F,
                viewBinding.root.right.toFloat(), leftTop.bottom
            )
            val leftBottom = RectF(
                0F, leftTop.bottom, leftTop.right,
                viewBinding.root.bottom.toFloat()
            )
            val rightBottom = RectF(
                leftBottom.right, leftTop.bottom,
                viewBinding.root.right.toFloat(), leftBottom.bottom
            )
            //分别获取对角线长度
            val leftTopHypotenuse = sqrt((leftTop.width().pow(2) + leftTop.height().pow(2)).toDouble())
            val rightTopHypotenuse = sqrt((rightTop.width().pow(2) + rightTop.height().pow(2)).toDouble())
            val leftBottomHypotenuse = sqrt((leftBottom.width().pow(2) + leftBottom.height().pow(2)).toDouble())
            val rightBottomHypotenuse = sqrt((rightBottom.width().pow(2) + rightBottom.height().pow(2)).toDouble())

            //取最大值 为最大半径
            maxRadius = leftTopHypotenuse.coerceAtLeast(rightTopHypotenuse)
                .coerceAtLeast(leftBottomHypotenuse.coerceAtLeast(rightBottomHypotenuse))
                .toInt()
        }
    }


    /**
     * 打开揭幕动画
     */
    private suspend fun picAnim() = withContext(Dispatchers.Default) {
        // 异步环境执行耗时的操作
        revealAnim = ViewAnimationUtils.createCircularReveal(
            viewBinding.ivPic,
            startX.toInt() + viewBinding.tv72.width / 2,
            startY.toInt() + viewBinding.tv72.height / 2,
            0F,
            maxRadius.toFloat()
        )

        revealAnim?.duration = 500L

        revealAnim?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
                viewBinding.ivPic.show()
            }

            override fun onAnimationEnd(p0: Animator) {
                viewBinding.ivPic.show()
                p0.removeAllListeners()
            }

            override fun onAnimationCancel(p0: Animator?) = Unit

            override fun onAnimationRepeat(p0: Animator?) = Unit

        })

        // 回到主线程执行
        viewBinding.root.post {
            revealAnim?.start()
        }
    }


    /**
     * 关闭揭幕动画
     */
    private fun resumePicAnim() {
        lifecycleScope.launch {

            // 逆向动画 反向半径参数
            revealAnim = ViewAnimationUtils.createCircularReveal(
                viewBinding.ivPic,
                startX.toInt() + viewBinding.tv72.width / 2,
                startY.toInt() + viewBinding.tv72.height / 2,
                maxRadius.toFloat(),
                0F
            )
            revealAnim?.duration = 500L
            revealAnim?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) = Unit

                override fun onAnimationEnd(p0: Animator) {
                    viewBinding.ivPic.hide()
                    p0.removeAllListeners()
                }

                override fun onAnimationCancel(p0: Animator?) = Unit

                override fun onAnimationRepeat(p0: Animator?) = Unit
            })

            viewBinding.root.post {
                revealAnim?.start()
            }
        }
    }

    // 设置顶部和底部的信息
    @Suppress("deprecation")
    private suspend fun initHeaderAndBottomBar() = withContext(Dispatchers.Main) {
        val currentDate = items[currentPosition].date.getDate()
        viewBinding.tv72.text = items[currentPosition].wuhou
        viewBinding.tv24.text = items[currentPosition].lunar
        viewBinding.tvCalendar.setText(
            mouth = (currentDate.month + 1).toString(),
            day = currentDate.date.toString()
        )
        viewBinding.tvLike.text = items[currentPosition].content.likeCount.toString()

        viewBinding.tvComment.text = items[currentPosition].content.commentCount.toString()
    }


    override fun onBackPressed() {
        if (viewBinding.ivPic.visibility == View.VISIBLE) {
            resumePicAnim()
            return
        }
        super.onBackPressed()
    }


    private fun initRecyclerview() {
        //监听以实现加变色转化动画
        sentenceAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.ScaleIn)
        cardLayoutManager.setOnViewPagerListener(object :
            ViewPagerLayoutManager.OnViewPagerListener {

            override fun onInitComplete() = Unit

            // 卡牌切换的监听
            override fun onPageSelected(position: Int, isBottom: Boolean) {
                currentPosition = position

                if (position == 0) {
                    // 根据滑动位置显示和隐藏"回到今天"
                    viewBinding.tvBackToday.hide()
                } else {
                    viewBinding.tvBackToday.show()
                }

                lifecycleScope.launch {
                    initHeaderAndBottomBar()
                }

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

                    // 生成新的渐变色背景并且应用
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

    // 传入页面
    override fun setupViewBinding(inflater: LayoutInflater):
            ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

}
