package com.neobis.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.neobis.databinding.WidgetHistorySugarItemBinding

import com.neobis.models.widgetModels.UserSugarResponse


class HistorySugarListAdapter : RecyclerView.Adapter<HistorySugarListAdapter.MyViewHolder>() {

    private var itemList = emptyList<UserSugarResponse>()


    class MyViewHolder(val binding: WidgetHistorySugarItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistorySugarListAdapter.MyViewHolder {
        return MyViewHolder(
            WidgetHistorySugarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.binding.itemValue.text = currentItem.value.toString()
        holder.binding.itemTime.text = currentItem.time.toString()
        if(currentItem.isNormal==true){
            holder.binding.rootLayout.setCardBackgroundColor(Color.parseColor("#83E786"))
        } else{
            holder.binding.rootLayout.setCardBackgroundColor(Color.parseColor("#FF7B7F"))
        }



    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(newData: List<UserSugarResponse>) {
        val diffUtil = CustomDiffUtil(itemList, newData)
        val diffUteilResult = DiffUtil.calculateDiff(diffUtil)
        itemList = newData
        diffUteilResult.dispatchUpdatesTo(this)
    }
}