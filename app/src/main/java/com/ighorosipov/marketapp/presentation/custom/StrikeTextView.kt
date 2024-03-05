package com.ighorosipov.marketapp.presentation.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ighorosipov.marketapp.R


class StrikeTextView @JvmOverloads constructor(
    private val context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var strikeColor = 0
    private var paint: Paint = Paint()

    init {
        init()
    }

    private fun init() {
        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.StrikeTextView,
            0,
            0
        )
        strikeColor = attributes.getColor(R.styleable.StrikeTextView_android_textColor, Color.BLACK).apply {
            setStrikeColor(this)
        }
        attributes.recycle()
        //replace with your desired width
        paint.strokeWidth = 2F
    }

    fun setStrikeColor(color: Int?) {
        if(color != null) {
            paint.color = color
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawLine(-2f, height.toFloat(), width.toFloat(), 2f, paint)
    }
}