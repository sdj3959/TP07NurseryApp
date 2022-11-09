package com.sdj2022.tp07nurseryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sdj2022.tp07nurseryapp.databinding.FragmentTripRecyclerItemBinding

class TripAdapter constructor(var context:Context, var items:MutableList<TripItem>) :Adapter<TripAdapter.VH>() {

    inner class VH(itemView:View) :ViewHolder(itemView){
        val binding = FragmentTripRecyclerItemBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var itemView:View = LayoutInflater.from(context).inflate(R.layout.fragment_trip_recycler_item, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.tvTitle.text = items[position].title
        holder.binding.tvAddress.text = items[position].spatial
    }

    override fun getItemCount(): Int = items.size
}