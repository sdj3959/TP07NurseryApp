package com.sdj2022.tp07nurseryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sdj2022.tp07nurseryapp.databinding.ActivityPhotoBinding

class PhotoActivity : AppCompatActivity() {

    val binding by lazy { ActivityPhotoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.iv.setOnClickListener{
            if(binding.clear.visibility == View.VISIBLE) binding.clear.visibility = View.INVISIBLE
            else binding.clear.visibility = View.VISIBLE
        }

        binding.clear.setOnClickListener{finish()}
    }
}