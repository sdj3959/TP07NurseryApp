package com.sdj2022.tp07nurseryapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sdj2022.tp07nurseryapp.databinding.NoteRecyclerItemBinding

class NoteAdapter constructor(val context: Context, var items:MutableList<NoteItem>):Adapter<NoteAdapter.VH>() {

    inner class VH(itemView:View):ViewHolder(itemView){
        val binding = NoteRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(context).inflate(R.layout.note_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item = items[position]
        holder.binding.tvDate.text = item.date
        Glide.with(context).load(item.image).into(holder.binding.iv)
        holder.binding.tvMessage.text = item.msg

        holder.binding.iv.setOnClickListener{
            var intent = Intent(context, PhotoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}