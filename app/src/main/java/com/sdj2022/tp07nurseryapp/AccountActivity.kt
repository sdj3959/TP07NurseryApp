package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.sdj2022.tp07nurseryapp.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    private val binding:ActivityAccountBinding by lazy{ ActivityAccountBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this).load(R.drawable.bg_nursery).into(binding.background)

        binding.btnLogin.isEnabled = false

        binding.etEmail.addTextChangedListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPw.text.toString()
            var enalbed = email.isNotEmpty() && password.isNotEmpty()
            binding.btnLogin.isEnabled = enalbed
        }

        binding.etPw.addTextChangedListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPw.text.toString()
            var enalbed = email.isNotEmpty() && password.isNotEmpty()
            binding.btnLogin.isEnabled = enalbed
        }

        binding.btnLogin.setOnClickListener {clickLogin()}
        binding.tvRegister.setOnClickListener {
            var intent = Intent(this, RegisterSelectActivity::class.java)
            startActivity(intent)
        }
    }
    
    fun clickLogin(){
        val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etPw.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else Toast.makeText(this, "이메일과 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
    }
}