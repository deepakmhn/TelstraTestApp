package com.telstra.testapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telstra.testapp.data.FactDetail
import com.telstra.testapp.databinding.FactsListItemBinding
import java.util.ArrayList

/**
 * Adapter used in the RecyclerView which loads the facts data
 */
class FactsRecyclerViewAdapter(private var items: ArrayList<FactDetail>)
    : RecyclerView.Adapter<FactsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FactsListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun replaceData(newItems: List<FactDetail>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: FactsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(factDetail: FactDetail) {
            binding.factDetail = factDetail
            binding.executePendingBindings()
        }
    }

}