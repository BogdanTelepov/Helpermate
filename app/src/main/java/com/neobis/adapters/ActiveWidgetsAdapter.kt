package com.neobis.adapters

import android.graphics.Color
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil


import androidx.recyclerview.widget.RecyclerView
import com.neobis.R

import com.neobis.databinding.WidgetActiveHomeLayoutBinding
import com.neobis.models.widgetModels.WidgetModel

import com.neobis.utils.WidgetColors

class ActiveWidgetsAdapter : RecyclerView.Adapter<ActiveWidgetsAdapter.WidgetViewHolder>() {


    private var listOfWidgets = emptyList<WidgetModel>()


    private var onItemClickListener: ((WidgetModel) -> Unit)? = null

    fun setOnClick(listener: (WidgetModel) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetViewHolder {
        return WidgetViewHolder(
            WidgetActiveHomeLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: WidgetViewHolder, position: Int) {
        val currentWidget = listOfWidgets[position]
        holder.binding.itemDescription.text = currentWidget.widgetDescription.toString().trim()

        holder.binding.itemName.text = currentWidget.widgetName?.trim()


        when (currentWidget.id) {
            0 -> {
                holder.binding.itemImage.setImageResource(R.drawable.ic_widget_water)
                holder.binding.rootLayout.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_WATER.color))
            }
            1 -> {
                holder.binding.itemImage.setImageResource(R.drawable.ic_widget_sleep)
                holder.binding.rootLayout.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_SLEEP.color))
            }
            2 -> {
                holder.binding.itemImage.setImageResource(R.drawable.ic_widget_foods)
                holder.binding.rootLayout.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_FOOD.color))
            }


            6 -> {
                holder.binding.itemImage.setImageResource(R.drawable.ic_widget_pharmacy)
                holder.binding.rootLayout.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_PHARMACY.color))
            }
            7 -> {
                holder.binding.itemImage.setImageResource(R.drawable.ic_widget_sugar)
                holder.binding.rootLayout.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_SUGAR.color))
            }
            8 -> {
                holder.binding.itemImage.setImageResource(R.drawable.ic_widget_activities)
                holder.binding.rootLayout.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_ACTIVITIES.color))
            }
            9 -> {
                holder.binding.itemImage.setImageResource(R.drawable.ic_widget_insulin)
                holder.binding.rootLayout.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_INSULIN.color))
            }
        }


        holder.itemView.apply {

            setOnClickListener {
                onItemClickListener?.let { it(currentWidget) }
            }
        }
    }

    fun setData(newData: List<WidgetModel>) {
        val diffUtil = CustomDiffUtil(listOfWidgets, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        listOfWidgets = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return listOfWidgets.size
    }

    class WidgetViewHolder(val binding: WidgetActiveHomeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}
