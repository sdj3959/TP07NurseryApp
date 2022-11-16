package com.sdj2022.tp07nurseryapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sdj2022.tp07nurseryapp.databinding.AlbumRecyclerItemBinding

class AlbumAdapter constructor(val context: Context, var items:MutableList<AlbumItem>) : Adapter<AlbumAdapter.VH>(){

    inner class VH (itemView:View) : ViewHolder(itemView){
        val binding = AlbumRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(context).inflate(R.layout.album_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item = items[position]
        holder.binding.tvMessage.text = item.msg
        var imgUrl = "http://dj2022.dothome.co.kr/NurseryApp/"+item.img
        Glide.with(context).load(imgUrl).into(holder.binding.iv)
        holder.binding.tvDate.text = item.date

        holder.binding.iv.setOnClickListener{
            var intent = Intent(context, PhotoActivity::class.java)
            intent.putExtra("img",imgUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}