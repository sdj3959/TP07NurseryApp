package com.sdj2022.tp07nurseryapp

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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

        binding.tvSave.setOnClickListener {
            if (binding.cbSave.isChecked) binding.cbSave.isChecked = false
            else binding.cbSave.isChecked = true
        }
        binding.tvAuto.setOnClickListener {
            if (binding.cbAuto.isChecked) binding.cbAuto.isChecked = false
            else binding.cbAuto.isChecked = true
        }

        loadData() //이메일 저장정보 불러오기

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
    
    private fun clickLogin(){
        binding.btnLogin.isEnabled = false
        val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etPw.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {

                    var dialog = ProgressDialog(this)
                    dialog.setMessage("로그인 중...")
                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    dialog.setCancelable(false)
                    dialog.show()

                    // 계정기억과 자동로그인 기능(SharedPreferences)
                    if(binding.cbSave.isChecked && !binding.cbAuto.isChecked){
                        val pref = getSharedPreferences("account", MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("email", binding.etEmail.text.toString())
                        editor.putString("save","1")
                        editor.putString("auto","0")
                        editor.commit()
                    }else if(binding.cbAuto.isChecked && !binding.cbSave.isChecked){
                        val pref = getSharedPreferences("account", MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("email", binding.etEmail.text.toString())
                        editor.putString("pw", binding.etPw.text.toString())
                        editor.putString("save", "0")
                        editor.putString("auto", "1")
                        editor.commit()
                    }else if(binding.cbAuto.isChecked && binding.cbSave.isChecked){
                        val pref = getSharedPreferences("account", MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("email", binding.etEmail.text.toString())
                        editor.putString("pw", binding.etPw.text.toString())
                        editor.putString("save", "1")
                        editor.putString("auto", "1")
                        editor.commit()
                    }


                    dialog.dismiss()

                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else Toast.makeText(this, "이메일과 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
    }//clickLogin()

    private fun loadData(){
        val pref = getSharedPreferences("account", MODE_PRIVATE)
        if(pref.getString("save", null).equals("1")) binding.etEmail.setText(pref.getString("email", null))
        if(pref.getString("save", null).equals("1")) binding.cbSave.isChecked = true
        if(pref.getString("auto", null).equals("1")) binding.cbAuto.isChecked = true
    }
}