package com.ighorosipov.marketapp.presentation.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ighorosipov.marketapp.R


class StrikeTextView : AppCompatTextView {
    private var dividerColor = 0
    private var paint: Paint? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val resources = context.resources
        //replace with your color
        dividerColor = resources.getColor(R.color.text_grey)
        paint = Paint()
        paint!!.color = dividerColor
        //replace with your desired width
        paint!!.strokeWidth = 2F
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(-2f, height.toFloat(), width.toFloat(), 2f, paint!!)
    }
}