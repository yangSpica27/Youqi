package com.spica.app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


const val HIGH_FIRST = 0 // 高位滚动速度快

const val LOW_FASTER = 1 // 高位滚动速度慢

const val ALL = 2 // 匀速

private const val MAX_LINE = 10 //滚动总行数

class SRandomTextView : AppCompatTextView {

    private var speedList: IntArray = intArrayOf() // 单元速度分配 数组

    private var speedSum = intArrayOf() // 总滚动距离

    private var overLine = intArrayOf() // 滚动完成的判断

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG) // 绘制文本的画笔

    private var isScroller = false // 是否正在滚动

    private var textIns = intArrayOf()

    private val textWidth = 0F // 文本宽度

    private var baseLine = 0F // 基线

    private var isFirst = true // 第一次进入

    private var text = "";

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isFirst) {
            isFirst = false
            val fontMetrics = textPaint.fontMetricsInt
            baseLine = ((height - fontMetrics.bottom + fontMetrics.top) / 2F - fontMetrics.top)
            val widths = FloatArray(text.length)
            paint.getTextWidths(text, widths)
            invalidate()
        }
        drawNumber(canvas)
    }


    /**
     * 绘制数字
     */
    private fun drawNumber(canvas: Canvas) {
        for (index in 0..text.length) {

            //滚动次数
            for (currentLine in 1..MAX_LINE) {

                if (currentLine == MAX_LINE - 1 && currentLine * baseLine + speedSum[index] <= baseLine) {

                    speedList[currentLine] = 0
                    overLine[currentLine] = 1
                    var autoOverLine = 0

                    for (k in 0..text.length) {
                        autoOverLine += overLine[k]
                    }

                }

            }

        }
    }

}
