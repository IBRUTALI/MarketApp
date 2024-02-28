package com.ighorosipov.marketapp.presentation.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
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

    private var titleGravity: Int = Gravity.START
    private var subtitleGravity: Int = Gravity.START

    private var leftIconGravity: Int = Gravity.CENTER
    private var rightIconGravity: Int = Gravity.CENTER

    private var titleTextColor: Int? = null
    private var subtitleTextColor: Int? = null

    private var titleTextAppearance: Int = 0
    private var subtitleTextAppearance: Int = 0

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
        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.MarketButton,
            defStyleAttr,
            R.style.TrueMaterialButton
        )
        title = attributes.getString(R.styleable.MarketButton_title)
        subtitle = attributes.getString(R.styleable.MarketButton_subtitle)
        titleTextColor = attributes.getColor(R.styleable.MarketButton_titleTextColor, Color.BLACK)
        subtitleTextColor =
            attributes.getColor(R.styleable.MarketButton_subtitleTextColor, Color.BLACK)
        titleTextAppearance = attributes.getResourceId(
            R.styleable.MarketButton_titleTextAppearance,
            com.google.android.material.R.attr.textAppearanceBodySmall
        )
        subtitleTextAppearance = attributes.getResourceId(
            R.styleable.MarketButton_subtitleTextAppearance,
            com.google.android.material.R.attr.textAppearanceBodySmall
        )
        attributes.getInteger(R.styleable.MarketButton_titleGravity, Gravity.CENTER_HORIZONTAL)
            .apply {
                titleGravity = mapIntToGravity(this)
            }
        subtitleGravity =
            attributes.getInteger(R.styleable.MarketButton_subtitleGravity, Gravity.START)
        leftIcon = attributes.getDrawable(R.styleable.MarketButton_leftIcon)
        rightIcon = attributes.getDrawable(R.styleable.MarketButton_rightIcon)
        leftIconGravity =
            attributes.getInteger(R.styleable.MarketButton_leftIconGravity, Gravity.CENTER_VERTICAL)
        rightIconGravity = attributes.getInteger(
            R.styleable.MarketButton_rightIconGravity,
            Gravity.CENTER_VERTICAL
        )
        leftIconSize = attributes.getDimension(R.styleable.MarketButton_leftIconSize, 32f)
        rightIconSize = attributes.getDimension(R.styleable.MarketButton_rightIconSize, 32f)
        enabled = attributes.getBoolean(R.styleable.MarketButton_state_enabled, true)
        attributes.recycle()
        isEnabled = enabled
        setTitle(title)
        setSubtitle(subtitle)
        setTitleTextColor(titleTextColor)
        setSubtitleTextColor(subtitleTextColor)
        setTitleTextAppearance(titleTextAppearance)
        setSubtitleTextAppearance(subtitleTextAppearance)
        setTitleGravity(titleGravity)
        setSubtitleGravity(subtitleGravity)
        setLeftIcon(leftIcon)
        setRightIcon(rightIcon)
        setLeftIconGravity(leftIconGravity)
        setRightIconGravity(rightIconGravity)
        setLeftIconSize(leftIconSize.toInt())
        setRightIconSize(rightIconSize.toInt())
    }

    fun setTitle(text: String?) {
        binding.title.text = text
    }

    fun setSubtitle(text: String?) {
        if (text.isNullOrBlank()) {
            binding.subtitle.visibility = GONE
        } else {
            binding.subtitle.visibility = VISIBLE
            binding.subtitle.text = text
        }
    }

    fun setTitleTextAppearance(resId: Int) {
        binding.title.setTextAppearance(resId)
    }

    fun setSubtitleTextAppearance(resId: Int) {
        binding.subtitle.setTextAppearance(resId)
    }

    fun setTitleGravity(grav: Int) {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        ).apply {
            weight = 1.0f
            gravity = grav
        }
        binding.title.layoutParams = params
    }

    fun setSubtitleGravity(grav: Int) {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        ).apply {
            weight = 1.0f
            gravity = grav
        }
        binding.subtitle.layoutParams = params
    }

    fun setLeftIcon(icon: Drawable?) {
        if (icon == null) {
            binding.leftIcon.visibility = GONE
        } else {
            binding.leftIcon.visibility = VISIBLE
            binding.leftIcon.setImageDrawable(icon)
        }
    }

    fun setRightIcon(icon: Drawable?) {
        if (icon == null) {
            binding.rightIcon.visibility = GONE
        } else {
            binding.rightIcon.visibility = VISIBLE
            binding.rightIcon.setImageDrawable(icon)
        }
    }

    fun setLeftIconGravity(grav: Int) {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = grav
        }
        binding.leftIcon.layoutParams = params
    }

    fun setRightIconGravity(grav: Int) {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = grav
        }
        binding.rightIcon.layoutParams = params
    }

    fun setLeftIconSize(size: Int) {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            width = size
            height = size
            gravity = leftIconGravity
        }
        binding.leftIcon.layoutParams = params
        binding.leftIcon.requestLayout()
    }

    fun setRightIconSize(size: Int) {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            width = size
            height = size
            gravity = rightIconGravity
        }
        binding.rightIcon.layoutParams = params
        binding.rightIcon.requestLayout()
    }

    fun setTitleTextColor(int: Int?) {
        if (int != null) {
            binding.title.setTextColor(int)
        }
    }

    fun setSubtitleTextColor(int: Int?) {
        if (int != null) {
            binding.subtitle.setTextColor(int)
        }
    }

    fun mapIntToGravity(int: Int): Int {
        return when (int) {
            0 -> Gravity.TOP
            1 -> Gravity.START
            2 -> Gravity.END
            3 -> Gravity.BOTTOM
            4 -> Gravity.CENTER
            5 -> Gravity.CENTER_VERTICAL
            6 -> Gravity.CENTER_HORIZONTAL
            else -> Gravity.NO_GRAVITY
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredWidth = 300 // Предполагаемая ширина View
        val desiredHeight = 150 // Предполагаемая высота View

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize // Задан конкретный размер для ширины
            MeasureSpec.AT_MOST -> min(
                desiredWidth,
                widthSize
            ) // Размер не должен превышать заданный размер
            else -> desiredWidth // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize // Задан конкретный размер для высоты
            MeasureSpec.AT_MOST -> min(
                desiredHeight,
                heightSize
            ) // Размер не должен превышать заданный размер
            else -> desiredHeight // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        setMeasuredDimension(width, height)
    }

    companion object {
        private val DEF_STYLE_RES: Int = R.style.TrueMaterialButton
    }

}