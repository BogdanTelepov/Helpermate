package com.neobis.utils

import androidx.recyclerview.widget.DiffUtil
import com.neobis.models.WidgetModel

class DiffUtilWidgetList : DiffUtil.ItemCallback<WidgetModel>() {

    override fun areItemsTheSame(oldItem: WidgetModel, newItem: WidgetModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WidgetModel, newItem: WidgetModel): Boolean {
        return oldItem.widgetName == newItem.widgetName
                && oldItem.widgetDescription == newItem.widgetDescription
                && oldItem.widgetColor == newItem.widgetColor
                && oldItem.widgetIcon == newItem.widgetIcon
    }
}