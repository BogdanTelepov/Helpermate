package com.neobis.adapters

import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.neobis.databinding.PharmacyItemLayoutBinding

import com.neobis.models.widgetModels.PharmacyModel


class PharmacyListAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<PharmacyListAdapter.PharmacyViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<PharmacyModel>() {
        override fun areItemsTheSame(oldItem: PharmacyModel, newItem: PharmacyModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PharmacyModel, newItem: PharmacyModel): Boolean {
            return oldItem.name == newItem.name
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    class PharmacyViewHolder(val binding: PharmacyItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {

        return PharmacyViewHolder(
            PharmacyItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.itemPharmacyName.text = currentItem.name
        holder.binding.ivDeleteItem.setOnClickListener {
            listener.deleteItem(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    interface OnItemClickListener {
        fun deleteItem(pharmacyModel: PharmacyModel)
    }
}