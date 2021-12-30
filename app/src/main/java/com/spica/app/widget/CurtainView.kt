package com.spica.app.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlin.math.log

@Suppress("unused")
class CurtainView : View, ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {


    private var background: Bitmap? = null // 背景截图

    private val paint = Paint()

    private var isStart = false

    private var duration = 1500L

    // 起始坐标

    private var startX = 0f

    private var startY = 0F

    // DecorView
    private var rootView: ViewGroup? = null

    //
    private var fraction = 100F


    // 动画结束的监听
    private var animationEndListener: () -> Unit = {}

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        rootView =  getActivityFromContext(context).window.decorView as ViewGroup
    }

    override fun onAnimationUpdate(anim: ValueAnimator) {
        fraction = anim.animatedValue as Float
        postInvalidate()
    }


    fun setDuration(duration: Long): CurtainView {
        this.duration = duration
        return this
    }


    fun addAnimEndListener(animationEndListener: () -> Unit): CurtainView {
        this.animationEndListener = animationEndListener
        return this
    }


    @Throws(Exception::class)
    private fun getActivityFromContext(mContext: Context): Activity {
        var context: Context = mContext
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        throw Exception("Activity not found!")
    }


    fun start() {
        if (!isStart) {
            Log.e("anim","kaisshi")
            isStart = true
            updateBackground()
            attachToRootView()
            getAnimator().start()
        }
    }

    /**
     * 放置到根视图
     */
    private fun attachToRootView() {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        rootView?.addView(this)
    }


    /**
     * 从根视图中移除并释放资源
     */
    private fun detachFromRootView() {
        rootView?.removeView(this)
        background?.let {
            if (!it.isRecycled) {
                it.recycle()
            }
        }
    }


    /**
     * 更新屏幕截图
     */
    private fun updateBackground() {
        background?.let {
            // 回收之前的bitmap
            if (!it.isRecycled) {
                it.recycle()
            }
        }
        // 获取新的屏幕截图
        background = rootView?.let { getBitmapFromView(it) }
    }


    /**
     * 由canvas更新背景截图
     */
    private fun getBitmapFromView(view: View): Bitmap {
        view.measure(
            MeasureSpec.makeMeasureSpec(view.layoutParams.width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(view.layoutParams.height, MeasureSpec.EXACTLY)
        )
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }


    // 获取动画对象
    private fun getAnimator(): ValueAnimator {
        val valueAnimator = ValueAnimator.ofFloat(0f, 100F)
            .setDuration(duration)
        valueAnimator.addUpdateListener(this)
        valueAnimator.addListener(this)
        return valueAnimator
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        Log.e("xuanran","-----")

        val layer = canvas.saveLayer(
            0F,
            0F,
            width.toFloat(),
            height.toFloat(),
            null
        )
        canvas.drawRect(
            0F,
            0F + (height / 2) * fraction,
            width.toFloat(),
            height - (height / 2) * fraction,
            paint
        )
        canvas.restoreToCount(layer)

    }

    // 动画开始
    override fun onAnimationStart(anim: Animator) = Unit

    // 动画结束
    override fun onAnimationEnd(anim: Animator) {
        isStart = false
        detachFromRootView()
        animationEndListener.invoke()
    }

    // 动画取消
    override fun onAnimationCancel(anim: Animator) = Unit

    // 动画重复
    override fun onAnimationRepeat(anim: Animator) = Unit


}