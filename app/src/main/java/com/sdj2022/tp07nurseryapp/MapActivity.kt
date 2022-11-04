package com.sdj2022.tp07nurseryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdj2022.tp07nurseryapp.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    val binding by lazy {ActivityMapBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}