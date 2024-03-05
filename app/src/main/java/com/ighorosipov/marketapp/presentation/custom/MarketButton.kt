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
    private var titleStrike: String? = null
    private var subtitle: String? = null

    private var textLayoutOrientation: Int = VERTICAL

    private var titleGravity: Int = Gravity.START
    private var titleStrikeGravity: Int = Gravity.CENTER_VERTICAL
    private var subtitleGravity: Int = Gravity.START

    private var leftIconGravity: Int = Gravity.CENTER
    private var rightIconGravity: Int = Gravity.CENTER

    private var titleTextColor: Int? = null
    private var titleStrikeTextColor: Int? = null
    private var subtitleTextColor: Int? = null

    private var titleTextAppearance: Int = 0
    private var titleStrikeTextAppearance: Int = 0
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
        title =
            attributes.getString(R.styleable.MarketButton_title).apply {
                setTitle(this)
            }
        subtitle =
            attributes.getString(R.styleable.MarketButton_subtitle).apply {
                setSubtitle(this)
            }
        titleStrike =
            attributes.getString(R.styleable.MarketButton_titleStrike).apply {
                setTitleStrike(this)
            }
        textLayoutOrientation =
            attributes.getInteger(R.styleable.MarketButton_textLayoutOrientation, HORIZONTAL).apply {
                setTextLayoutOrientation(this)
            }
        titleTextAppearance =
            attributes.getResourceId(
                R.styleable.MarketButton_titleTextAppearance,
                com.google.android.material.R.attr.textAppearanceBodySmall
            ).apply {
                setTitleTextAppearance(this)
            }
        titleStrikeTextAppearance =
            attributes.getResourceId(
                R.styleable.MarketButton_titleStrikeTextAppearance,
                com.google.android.material.R.attr.textAppearanceBodySmall
            ).apply {
                setTitleStrikeTextAppearance(this)
            }
        subtitleTextAppearance =
            attributes.getResourceId(
                R.styleable.MarketButton_subtitleTextAppearance,
                com.google.android.material.R.attr.textAppearanceBodySmall
            ).apply {
                setSubtitleTextAppearance(this)
            }
        titleTextColor =
            attributes.getColor(
                R.styleable.MarketButton_titleTextColor,
                binding.title.currentTextColor
            ).apply {
                setTitleTextColor(this)
            }
        titleStrikeTextColor =
            attributes.getColor(
                R.styleable.MarketButton_titleStrikeTextColor,
                binding.titleStrike.currentTextColor
            ).apply {
                setTitleStrikeTextColor(this)
            }
        subtitleTextColor =
            attributes.getColor(
                R.styleable.MarketButton_subtitleTextColor,
                binding.subtitle.currentTextColor
            ).apply {
                setSubtitleTextColor(this)
            }
        attributes.getInteger(R.styleable.MarketButton_titleGravity, Gravity.CENTER)
            .apply {
                titleGravity = mapIntToGravity(this)
                setTextLayoutGravity(titleGravity)
            }
        attributes.getInteger(R.styleable.MarketButton_subtitleGravity, Gravity.START)
            .apply {
                subtitleGravity = mapIntToGravity(this)
                setSubtitleGravity(subtitleGravity)
            }
        attributes.getInteger(R.styleable.MarketButton_titleStrikeGravity, Gravity.CENTER_VERTICAL)
            .apply {
                titleStrikeGravity = mapIntToGravity(this)
                setTitleStrikeGravity(titleStrikeGravity)
            }
        leftIcon =
            attributes.getDrawable(R.styleable.MarketButton_leftIcon).apply {
                setLeftIcon(this)
            }
        rightIcon =
            attributes.getDrawable(R.styleable.MarketButton_rightIcon).apply {
                setRightIcon(this)
            }
        leftIconGravity =
            attributes.getInteger(
                R.styleable.MarketButton_leftIconGravity,
                Gravity.CENTER_VERTICAL
            ).apply {
                setLeftIconGravity(this)
            }
        rightIconGravity =
            attributes.getInteger(
                R.styleable.MarketButton_rightIconGravity,
                Gravity.CENTER_VERTICAL
            ).apply {
                setRightIconGravity(this)
            }
        leftIconSize =
            attributes.getDimension(R.styleable.MarketButton_leftIconSize, 32f).apply {
                setLeftIconSize(this.toInt())
            }
        rightIconSize =
            attributes.getDimension(R.styleable.MarketButton_rightIconSize, 32f).apply {
                setRightIconSize(this.toInt())
            }
        enabled =
            attributes.getBoolean(R.styleable.MarketButton_state_enabled, true).apply {
                isEnabled = this
            }
        attributes.recycle()
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

    fun setTitleStrike(text: String?) {
        if (text.isNullOrBlank()) {
            binding.titleStrike.visibility = GONE
        } else {
            binding.titleStrike.visibility = VISIBLE
            binding.titleStrike.text = text
        }
    }

    fun setTitleTextAppearance(resId: Int) {
        binding.title.setTextAppearance(resId)
    }

    fun setSubtitleTextAppearance(resId: Int) {
        binding.subtitle.setTextAppearance(resId)
    }

    fun setTitleStrikeTextAppearance(resId: Int) {
        binding.titleStrike.setTextAppearance(resId)
    }

    fun setTextLayoutGravity(grav: Int) {
        val width = LayoutParams.WRAP_CONTENT
        val height = LayoutParams.MATCH_PARENT
        val params = LayoutParams(
            width,
            height
        ).apply {
            weight = 1.0f
            gravity = grav
        }
        binding.titleLayout.layoutParams = params
    }

    fun setTitleGravity(grav: Int) {
        var width = LayoutParams.MATCH_PARENT
        var height = LayoutParams.MATCH_PARENT
        if (isHorizontalOrientation(textLayoutOrientation)) {
            width = LayoutParams.WRAP_CONTENT
            height = LayoutParams.WRAP_CONTENT
        }
        val params = LayoutParams(
            width,
            height
        ).apply {
            gravity = grav
        }
        binding.title.layoutParams = params
    }

    fun setTitleStrikeGravity(grav: Int) {
        val width = LayoutParams.WRAP_CONTENT
        val height = LayoutParams.WRAP_CONTENT
        val params = LayoutParams(
            width,
            height
        ).apply {
            marginStart = 26
            gravity = grav
        }
        binding.titleStrike.layoutParams = params
    }

    fun setSubtitleGravity(grav: Int) {
        var width = LayoutParams.WRAP_CONTENT
        var height = LayoutParams.WRAP_CONTENT
        if (isHorizontalOrientation(textLayoutOrientation)) {
            width = LayoutParams.WRAP_CONTENT
            height = LayoutParams.WRAP_CONTENT
        }
        val params = LayoutParams(
            width,
            height
        ).apply {
            gravity = grav
        }
        binding.subtitle.layoutParams = params
    }

    fun setTextLayoutOrientation(orientation: Int) {
        when(orientation) {
            HORIZONTAL -> {
                binding.textLayout.orientation = orientation
            }
            VERTICAL -> {
                binding.textLayout.orientation = orientation
            }
            else -> return
        }
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

    fun setTitleTextColor(color: Int?) {
        if (color != null) {
            binding.title.setTextColor(color)
        }
    }

    fun setTitleStrikeTextColor(color: Int?) {
        if (color != null) {
            binding.titleStrike.setStrikeColor(color)
            binding.titleStrike.setTextColor(color)
        }
    }

    fun setSubtitleTextColor(color: Int?) {
        if (color != null) {
            binding.subtitle.setTextColor(color)
        }
    }

    private fun isHorizontalOrientation(orientation: Int): Boolean {
        return orientation == HORIZONTAL
    }

    private fun mapIntToGravity(int: Int): Int {
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
        val desiredHeight = 135 // Предполагаемая высота View

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
        private val DEF_TEXT_COLOR: Int = Color.BLACK
    }

}