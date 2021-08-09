package com.neobis.adapters


import android.graphics.Color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.neobis.R
import com.neobis.models.widgetModels.WidgetModel
import com.neobis.utils.DiffUtilWidgetList
import com.neobis.utils.WidgetColors


class WidgetListAdapter : RecyclerView.Adapter<WidgetListAdapter.WidgetViewHolder>() {

    val differ = AsyncListDiffer(this, DiffUtilWidgetList())

    private var onItemClickListener: ((WidgetModel) -> Unit)? = null

    fun setOnClick(listener: (WidgetModel) -> Unit) {
        onItemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.widget_item_layout, parent, false)
        return WidgetViewHolder(view)
    }


    override fun onBindViewHolder(holder: WidgetViewHolder, position: Int) {
        val currentWidget = differ.currentList[position]
        holder.item_description.text = currentWidget.widgetDescription.toString().trim()

        holder.item_name.text = currentWidget.widgetName?.trim()


        when (currentWidget.id) {
            0 -> {
                holder.item_image.setImageResource(R.drawable.ic_widget_water)
                holder.cardView.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_WATER.color))
            }
            1 -> {
                holder.item_image.setImageResource(R.drawable.ic_widget_sleep)
                holder.cardView.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_SLEEP.color))
            }
            2 -> {
                holder.item_image.setImageResource(R.drawable.ic_widget_foods)
                holder.cardView.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_FOOD.color))
            }
            3 -> {

            }
            4 -> {

            }
            5 -> {

            }
            6 -> {
                holder.item_image.setImageResource(R.drawable.ic_widget_pharmacy)
                holder.cardView.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_PHARMACY.color))
            }
            7 -> {
                holder.item_image.setImageResource(R.drawable.ic_widget_sugar)
                holder.cardView.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_SUGAR.color))
            }
            8 -> {
                holder.item_image.setImageResource(R.drawable.ic_widget_activities)
                holder.cardView.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_ACTIVITIES.color))
            }
            9 -> {
                holder.item_image.setImageResource(R.drawable.ic_widget_insulin)
                holder.cardView.setCardBackgroundColor(Color.parseColor(WidgetColors.WIDGET_INSULIN.color))
            }
        }


        holder.itemView.apply {

            setOnClickListener {
                onItemClickListener?.let { it(currentWidget) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class WidgetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val item_name: TextView = view.findViewById(R.id.item_name)
        val item_description: TextView = view.findViewById(R.id.item_description)
        val item_image: ImageView = view.findViewById(R.id.item_image)
        val cardView: CardView = view.findViewById(R.id.rootLayout)

    }
}