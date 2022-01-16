package cn.tagux.calendar.widget


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import cn.tagux.calendar.R
import cn.tagux.calendar.extensions.dp
import cn.tagux.calendar.extensions.sp


class SpicaTextView : View {

    /**
     * 日
     */
    private var day: String = "30"


    /**
     * 月
     */
    private var mouth: String = "01";


    /**
     * 绘制月份的骨架
     */
    private var mouthBound: Rect = Rect()

    /**
     * 绘制日期的骨架
     */
    private var dayBound: Rect = Rect()

    private val textPaint: TextPaint = TextPaint()

    private val linePaint: Paint = Paint()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        initStyle(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initStyle(attrs)
    }

    init {
        textPaint.color = Color.WHITE
        textPaint.textSize = 18.sp.toFloat()
        linePaint.color = Color.WHITE
        linePaint.strokeWidth = 2.dp.toFloat()


    }

    private fun initStyle(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SpicaTextView)
        mouth = a.getString(R.styleable.SpicaTextView_mouth) ?: "01"
        day = a.getString(R.styleable.SpicaTextView_day) ?: "01"

        textPaint.color = a.getColor(R.styleable.SpicaTextView_textColor, Color.WHITE)
        linePaint.color = a.getColor(R.styleable.SpicaTextView_lineColor, Color.WHITE)

        a.recycle()
    }


    /**
     * 重新设置文本颜色
     */
    fun setTextColor(@ColorInt textColor: Int) {
        textPaint.color = textColor
        invalidate()
    }

    /**
     * 重新设置线条颜色
     */
    fun setLineColor(@ColorInt lineColor: Int) {
        linePaint.color = lineColor
        invalidate()
    }

    /**
     * 重新设置文本内容
     */
    fun setText(mouth: String, day: String) {
        this.day = day
        this.mouth = mouth
        invalidate()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //测量日期文本所需空间
        textPaint.getTextBounds(day, 0, day.length, dayBound)
        //重新测量宽度确定右界位置
        dayBound.right = dayBound.left +
                textPaint.measureText(day, 0, day.length).toInt()
        //测量月份文本所需空间
        textPaint.getTextBounds(mouth, 0, mouth.length, mouthBound)
        //绘制月份
        canvas.drawText(mouth, 0F, (0 + mouthBound.height()).toFloat(), textPaint)
        //绘制日
        canvas.drawText(day, (width - dayBound.width()).toFloat(), (height).toFloat(), textPaint)
        //绘制斜线
        canvas.drawLine(width.toFloat(), 0F, 0F, height.toFloat(), linePaint
        )
    }
}