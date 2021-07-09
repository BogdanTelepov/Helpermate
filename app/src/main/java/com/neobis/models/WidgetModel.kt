package com.neobis.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WidgetModel(
    val id: Int?,
    val widgetName: String?,
    val widgetIcon: Int?,
    val widgetColor: String?,
    val widgetDescription: String?

) : Parcelable

