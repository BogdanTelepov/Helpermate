package com.neobis.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WidgetModel(
    val widgetName: String,
    val widgetIcon: Int,
    val widgetColor: Int,
    val widgetDescription: String?

) : Parcelable

