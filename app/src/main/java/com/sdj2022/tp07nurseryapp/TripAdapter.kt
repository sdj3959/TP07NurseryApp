package com.sdj2022.tp07nurseryapp

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sdj2022.tp07nurseryapp.databinding.FragmentTripRecyclerItemBinding

class TripAdapter constructor(var context:Context, var items:MutableList<TripItem>) :Adapter<TripAdapter.VH>() {

    inner class VH(itemView:View) :ViewHolder(itemView){
        val binding = FragmentTripRecyclerItemBinding.bind(itemView)

        var title = itemView.findViewById<TextView>(R.id.tv_title)
        var address = itemView.findViewById<TextView>(R.id.tv_address)
        var tvMap = itemView.findViewById<TextView>(R.id.tv_map)

    }

    lateinit var itemView: View

    val FIRST_TYPE = 0
    val SECOND_TYPE = 1
    val THIRD_TYPE = 2
    val FOURTH_TYPE = 3
    val FIFTH_TYPE = 4

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0-> FIRST_TYPE
            1-> SECOND_TYPE
            2-> THIRD_TYPE
            3-> FOURTH_TYPE
            4-> FIFTH_TYPE
            else -> 0
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        //var itemView:View
        when(viewType){
            FIRST_TYPE -> itemView = LayoutInflater.from(context).inflate(R.layout.fragment_trip_recycler_item_best, parent, false)
            SECOND_TYPE -> itemView = LayoutInflater.from(context).inflate(R.layout.fragment_trip_recycler_item, parent, false)
            THIRD_TYPE -> itemView = LayoutInflater.from(context).inflate(R.layout.fragment_trip_recycler_item2, parent, false)
            FOURTH_TYPE -> itemView = LayoutInflater.from(context).inflate(R.layout.fragment_trip_recycler_item3, parent, false)
            FIFTH_TYPE -> itemView = LayoutInflater.from(context).inflate(R.layout.fragment_trip_recycler_item4, parent, false)
        }
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val title = HtmlCompat.fromHtml(items[position].title, HtmlCompat.FROM_HTML_MODE_COMPACT)

        //val regex = "[一-龥]".toRegex()
        val regex = "[^가-힣]".toRegex()
        var s = title.toString().replace(regex,"")

        holder.title.text = s.replace(" ","")
        if(items[position].spatial != null){
            holder.address.text = items[position].spatial
        } else {
            holder.address.text = items[position].url
            //holder.tvMap.visibility = View.GONE
            holder.tvMap.setTextColor(context.getColor(R.color.gray))
            holder.tvMap.text = "지도 정보가 없어요"
        }

        holder.binding.tvMap.setOnClickListener {
            if(holder.tvMap.text.equals("누르면 지도가 보여요!")) {
                var intent = Intent(context, MapActivity::class.java)
                intent.putExtra("addr", items[position].spatial)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = items.size
}