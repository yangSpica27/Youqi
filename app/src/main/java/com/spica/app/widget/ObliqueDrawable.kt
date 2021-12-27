package com.spica.app.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.spica.app.extensions.dp


class ObliqueLinerLayout : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val path = Path()

    private var isTop = false

    private var isBottom = false

    fun setMode(isTop: Boolean = false, isBottom: Boolean = false) {
        this.isTop = isTop
        this.isBottom = isBottom
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (isTop) {
            path.moveTo(0f, 0f)
        } else {
            path.moveTo(0f, 15.dp.toFloat())
        }

        path.lineTo(width.toFloat(), 0f)

        if (isBottom) {
            path.lineTo(width.toFloat(), height.toFloat())
        } else {
            path.lineTo(width.toFloat(), height.toFloat() - 15.dp)
        }

        path.lineTo(0F, height.toFloat())

        path.close()

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        canvas.clipPath(path)

        super.onDraw(canvas)

    }


}