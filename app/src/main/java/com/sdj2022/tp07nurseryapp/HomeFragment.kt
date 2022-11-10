package com.sdj2022.tp07nurseryapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    companion object{



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Glide.with(this).load(GUserData.userData["profile"]).into(binding?.profile!!)
        binding?.tvNursery?.text = GUserData.userData["nursery"]

        if(GUserData.userData["position"].equals("0")) binding?.tvName?.text = "${GUserData.userData["name"]} 부모님"
        else if(GUserData.userData["position"].equals("1")) binding?.tvName?.text = "${GUserData.userData["name"]} 교사"
        else binding?.tvName?.text = "${GUserData.userData["name"]} 원장"





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
            2-> Intent(activity, NoteManagerActivity::class.java)
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