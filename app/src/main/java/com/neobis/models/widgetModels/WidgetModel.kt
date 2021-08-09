package com.neobis.models.widgetModels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WidgetModel(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val widgetName: String?,
    val widgetIcon: Int?,
    val widgetColor: String?,
    val widgetDescription: String?

) : Parcelable

