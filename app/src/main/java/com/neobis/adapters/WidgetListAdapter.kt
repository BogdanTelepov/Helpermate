package com.neobis.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.neobis.R
import com.neobis.models.WidgetModel
import com.neobis.utils.DiffUtilWidgetList


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
        holder.item_image.setImageResource(currentWidget.widgetIcon!!)
        holder.item_name.text = currentWidget.widgetName?.trim()
        holder.cardView.setCardBackgroundColor(Color.parseColor(currentWidget.widgetColor))
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