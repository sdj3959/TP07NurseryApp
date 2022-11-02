package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.sdj2022.tp07nurseryapp.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    private val binding:ActivityAccountBinding by lazy{ ActivityAccountBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this).load(R.drawable.bg_nursery).into(binding.background)

        binding.btnLogin.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.tvRegister.setOnClickListener {
            var intent = Intent(this, RegisterSelectActivity::class.java)
            startActivity(intent)
        }
    }
}