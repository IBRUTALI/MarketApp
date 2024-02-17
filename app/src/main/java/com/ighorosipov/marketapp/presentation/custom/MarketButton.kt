package com.ighorosipov.marketapp.presentation.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Px
import com.google.android.material.theme.overlay.MaterialThemeOverlay
import com.ighorosipov.marketapp.R
import com.ighorosipov.marketapp.databinding.ButtonMarketBinding


class MarketButton : LinearLayout {
    private var _binding: ButtonMarketBinding? = null
    private val binding get() = _binding!!
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
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES),
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        _binding = ButtonMarketBinding.inflate(LayoutInflater.from(context), this, false)
        addView(binding.root)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.MarketButton, defStyleAttr, R.style.TrueMaterialButton)
        leftIcon = attributes.getDrawable(R.styleable.MarketButton_leftIcon)
        rightIcon = attributes.getDrawable(R.styleable.MarketButton_rightIcon)
        leftIconSize = attributes.getDimension(R.styleable.MarketButton_leftIconSize, 0f)
        rightIconSize = attributes.getDimension(R.styleable.MarketButton_rightIconSize, 0f)
        binding.apply {
            leftDrawable.setImageDrawable(leftIcon)
            rightDrawable.setImageDrawable(rightIcon)

        }
        attributes.recycle()
    }
    companion object {
        private val DEF_STYLE_RES: Int = R.style.TrueMaterialButton
    }

}