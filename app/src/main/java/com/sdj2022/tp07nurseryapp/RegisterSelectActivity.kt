package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.sdj2022.tp07nurseryapp.databinding.ActivityRegisterSelectBinding

class RegisterSelectActivity : AppCompatActivity() {

    val binding by lazy { ActivityRegisterSelectBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)


        binding.btnParent.setOnClickListener {
            var intent = Intent(this, RegisterParentActivity::class.java)
            startActivity(intent)
        }
        binding.btnTeacher.setOnClickListener {
            var intent = Intent(this, RegisterTeacherActivity::class.java)
            startActivity(intent)
        }
        binding.btnDirector.setOnClickListener {
            var intent = Intent(this, RegisterDirectorActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}