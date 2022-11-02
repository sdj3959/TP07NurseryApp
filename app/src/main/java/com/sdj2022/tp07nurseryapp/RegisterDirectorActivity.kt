package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdj2022.tp07nurseryapp.databinding.ActivityRegisterDirectorBinding

class RegisterDirectorActivity : AppCompatActivity() {

    val binding by lazy { ActivityRegisterDirectorBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.btnComplete.setOnClickListener {
            finish()
            var intent = Intent(this, AccountActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}