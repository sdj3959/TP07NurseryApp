package com.sdj2022.tp07nurseryapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sdj2022.tp07nurseryapp.databinding.NoticeRecyclerItemBinding

class NoticeAdapter constructor(val context: Context, var items:MutableList<NoticeItem>) : Adapter<NoticeAdapter.VH>() {

    inner class VH constructor(itemView:View):ViewHolder(itemView){
        val binding = NoticeRecyclerItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(context).inflate(R.layout.notice_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        var item = items[position]

        Glide.with(context).load(item.profile).into(holder.binding.civProfile)
        holder.binding.tvName.text = item.name
        holder.binding.tvDate.text = item.date
        var imgUrl = "http://dj2022.dothome.co.kr/NurseryApp/"+item.img
        Glide.with(context).load(imgUrl).into(holder.binding.iv)
        holder.binding.tvTitle.text = item.title
        holder.binding.tvMessage.text = item.msg

        holder.binding.iv.setOnClickListener{
            var intent = Intent(context, PhotoActivity::class.java)
            intent.putExtra("img",imgUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}