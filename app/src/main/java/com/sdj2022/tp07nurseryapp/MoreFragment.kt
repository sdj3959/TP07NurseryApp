package com.sdj2022.tp07nurseryapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sdj2022.tp07nurseryapp.databinding.FragmentHomeBinding
import com.sdj2022.tp07nurseryapp.databinding.FragmentMoreBinding

class MoreFragment:Fragment() {

    var binding:FragmentMoreBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.ivInfo?.setOnClickListener{ startActivity(Intent(activity, NurseryInfoActivity::class.java))}
        binding?.ivLogout?.setOnClickListener {
            val pref = activity?.getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
            val editor = pref?.edit()
            editor?.putString("auto","0")
            editor?.commit()

            startActivity(Intent(activity, AccountActivity::class.java))
            activity?.finish()
        }
    }
}