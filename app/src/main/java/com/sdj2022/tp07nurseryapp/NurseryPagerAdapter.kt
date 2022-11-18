package com.sdj2022.tp07nurseryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sdj2022.tp07nurseryapp.databinding.NurseryPagerBinding

class NurseryPagerAdapter constructor(val context: Context, var items:MutableList<NurseryPagerItem>): Adapter<NurseryPagerAdapter.VH>() {
    inner class VH(itemView:View) : ViewHolder(itemView){
        val binding = NurseryPagerBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(context).inflate(R.layout.nursery_pager, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(items[position].profile).into(holder.binding.profile)
        holder.binding.name.text = items[position].name
        holder.binding.nursery.text = items[position].nursery
    }

    override fun getItemCount(): Int = items.size
}