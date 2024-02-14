package com.ighorosipov.marketapp.presentation.catalog

import androidx.annotation.StringRes
import com.ighorosipov.marketapp.R

sealed class Tag(@StringRes val resId: Int, val tag: String) {
    data class All(@StringRes val id: Int = R.string.all, val tagValue: String = "all") : Tag(id, tagValue)
    data class Face(@StringRes val id: Int = R.string.face, val tagValue: String = "face") : Tag(id, tagValue)
    data class Body(@StringRes val id: Int = R.string.body, val tagValue: String = "body") : Tag(id, tagValue)
    data class Suntan(@StringRes val id: Int = R.string.suntan, val tagValue: String = "suntan") : Tag(id, tagValue)
    data class Mask(@StringRes val id: Int = R.string.mask, val tagValue: String = "mask") : Tag(id, tagValue)
}