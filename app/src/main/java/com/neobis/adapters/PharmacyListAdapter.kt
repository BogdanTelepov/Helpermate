package com.neobis.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.R
import com.neobis.models.PharmacyModel

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pharmacy_item_layout, parent, false)
        return PharmacyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PharmacyViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.itemName.text = currentItem.name
        holder.itemDelete.setOnClickListener {
            listener.deleteItem(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class PharmacyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val itemName: TextView = view.findViewById(R.id.item_pharmacy_name)
        val itemDelete: ImageView = view.findViewById(R.id.iv_delete_item)


    }

    interface OnItemClickListener {
        fun deleteItem(pharmacyModel: PharmacyModel)
    }
}