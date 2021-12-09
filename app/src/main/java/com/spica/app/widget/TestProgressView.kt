package com.spica.app.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.spica.app.extensions.dp


class TestProgressView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        invalidate()
    }
    // 圆弧起始角度
    private var startAngle = 0F

    // 圆弧最终角度
    private var endAngle = 180F

    // 圆弧背景的开始和结束间的夹角大小
    private val bgAngle = 200f

    // 当前进度下夹角大小
    private var includeAngle = (endAngle - startAngle) * (.5F)

    companion object {
        private val STROKE_WIDTH = 4.dp

        private val DASH_STROKE_WIDTH = 2.dp
    }


    // 绘制圆弧的paint
    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#4DB6AC")
        // 连接处改成圆角
        strokeJoin = Paint.Join.ROUND
        // 节点为圆角
        strokeJoin = Paint.Join.ROUND

        //设置画笔属性
        style = Paint.Style.STROKE

        strokeWidth = STROKE_WIDTH.toFloat();
    }

    // 绘制圆弧的paint
    private val arcPaint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#4DB6AC")
        // 连接处改成圆角
        strokeJoin = Paint.Join.ROUND
        // 节点为圆角
        strokeJoin = Paint.Join.ROUND

        //设置画笔属性
        style = Paint.Style.STROKE

        strokeWidth = DASH_STROKE_WIDTH.toFloat();

        pathEffect = DashPathEffect(floatArrayOf(1.dp.toFloat(), 6.dp.toFloat()), 0F)
    }

    private val rectF = RectF(
        STROKE_WIDTH + 5.dp.toFloat(),
        STROKE_WIDTH + 5.dp.toFloat(),
        width - STROKE_WIDTH - 5.dp.toFloat(),
        height - 5.dp.toFloat()
    )

    // 虚线圆弧

    private val rectF2 = RectF(
        STROKE_WIDTH + 20.dp.toFloat(),
        STROKE_WIDTH + 20.dp.toFloat(),
        width - STROKE_WIDTH - 20.dp.toFloat(),
        height - 20.dp.toFloat()
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(rectF, startAngle, endAngle, false, arcPaint)

        canvas.drawArc(rectF, startAngle, includeAngle, false, arcPaint);

        canvas.drawArc(rectF2,startAngle,endAngle,false,arcPaint2)


    }
}