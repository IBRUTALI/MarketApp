package com.ighorosipov.marketapp.presentation.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Px
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.ButtonMarketBinding
import kotlin.math.min


class MarketButton @JvmOverloads constructor(
    private val context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding = ButtonMarketBinding.inflate(LayoutInflater.from(context), this, false)

    private var title: String? = null
    private var subtitle: String? = null

    private var enabled: Boolean = true

    private var leftIcon: Drawable? = null
    private var rightIcon: Drawable? = null

    private var leftIconTintMode: PorterDuff.Mode? = null
    private var rightIconTintMode: PorterDuff.Mode? = null
    private val leftIconTint: ColorStateList? = null
    private val rightIconTint: ColorStateList? = null

    @Px
    private var leftIconSize = 0f
    @Px
    private var rightIconSize = 0f

    @Px
    private var leftIconPadding = 0
    @Px
    private var rightIconPadding = 0

    init {
        init()
    }

    private fun init() {
        addView(binding.root)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MarketButton, defStyleAttr, R.style.TrueMaterialButton)
        title = attributes.getString(R.styleable.MarketButton_title)
        subtitle = attributes.getString(R.styleable.MarketButton_subtitle)
        leftIcon = attributes.getDrawable(R.styleable.MarketButton_leftIcon)
        rightIcon = attributes.getDrawable(R.styleable.MarketButton_rightIcon)
        leftIconSize = attributes.getDimension(R.styleable.MarketButton_leftIconSize, 32f)
        rightIconSize = attributes.getDimension(R.styleable.MarketButton_rightIconSize, 32f)
        enabled = attributes.getBoolean(R.styleable.MarketButton_state_enabled, true)
        attributes.recycle()
        isEnabled = enabled
        setTitle(title)
        setSubTitle(subtitle)
        setLeftIcon(leftIcon)
        setRightIcon(rightIcon)
//        setLeftIconSize(leftIconSize.toInt())
//        setRightIconSize(rightIconSize.toInt())
    }

    fun setTitle(text: String?){
        binding.title.text = text
    }

    fun setSubTitle(text: String?) {
        binding.subtitle.text = text
    }

    fun setLeftIcon(icon: Drawable?) {
        binding.leftIcon.setImageDrawable(icon)
    }

    fun setRightIcon(icon: Drawable?) {
        binding.rightIcon.setImageDrawable(icon)
    }

    fun setLeftIconSize(size: Int) {
        binding.leftIcon.layoutParams = LayoutParams(size, size)
        binding.leftIcon.requestLayout()
    }

    fun setRightIconSize(size: Int) {
        binding.rightIcon.layoutParams = LayoutParams(size, size)
        binding.rightIcon.requestLayout()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = 300 // Предполагаемая ширина View
        val desiredHeight = 170 // Предполагаемая высота View

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize // Задан конкретный размер для ширины
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize) // Размер не должен превышать заданный размер
            else -> desiredWidth // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize // Задан конкретный размер для высоты
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize) // Размер не должен превышать заданный размер
            else -> desiredHeight // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        setMeasuredDimension(width, height)
    }

    companion object {
        private val DEF_STYLE_RES: Int = R.style.TrueMaterialButton
    }

}