package com.sdj2022.tp07nurseryapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sdj2022.tp07nurseryapp.databinding.FragmentHomeBinding

class HomeFragment:Fragment() {

    var binding:FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.click?.setOnClickListener{ clickMenu(0) }
        binding?.album?.setOnClickListener { clickMenu(1) }
        binding?.note?.setOnClickListener { clickMenu(2) }
        binding?.calendar?.setOnClickListener { clickMenu(3) }
        binding?.foodmenu?.setOnClickListener { clickMenu(4) }
    }

    fun clickMenu(menus:Int){
        var intent = when(menus){
            0-> Intent(activity, NoticeActivity::class.java)
            1-> Intent(activity, AlbumActivity::class.java)
            2-> Intent(activity, NoteParentActivity::class.java)
            3-> Intent(activity, CalendarActivity::class.java)
            4-> Intent(activity, FoodMenuActivity::class.java)
            else-> null
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}