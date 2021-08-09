package com.neobis.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.databinding.WidgetHistoryItemBinding
import com.neobis.models.history.SleepHistoryResponseItem

class HistoryListAdapter : RecyclerView.Adapter<HistoryListAdapter.MyViewHolder>() {

    private var itemList = emptyList<SleepHistoryResponseItem>()


    class MyViewHolder(val binding: WidgetHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryListAdapter.MyViewHolder {
        return MyViewHolder(
            WidgetHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryListAdapter.MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.binding.itemCurrentDay.text = "Сегодня"
        holder.binding.itemCurrentTime.text = currentItem.createdDate?.substring(11, 16)
        holder.binding.itemDescription.text = "${currentItem.endTime} - ${currentItem.startTime}"
        holder.binding.itemOverAllMasse.text = ""
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(newData: List<SleepHistoryResponseItem>) {
        val diffUtil = CustomDiffUtil(itemList, newData)
        val diffUteilResult = DiffUtil.calculateDiff(diffUtil)
        itemList = newData
        diffUteilResult.dispatchUpdatesTo(this)
    }
}