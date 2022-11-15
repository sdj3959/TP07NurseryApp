package com.sdj2022.tp07nurseryapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.sdj2022.tp07nurseryapp.databinding.ActivityCalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    val binding by lazy {ActivityCalendarBinding.inflate(layoutInflater)}

    lateinit var imgUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.tvDate.text = SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        if(GUserData.userData["position"].equals("0")) binding.fab.visibility = View.GONE
        binding.fab.setOnClickListener{
//            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var intent = Intent(Intent.ACTION_PICK).setType("image/*")
            resultLauncher.launch(intent)
        }

        binding.iv.setOnClickListener{
            var intent = Intent(this, PhotoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
        if (it.resultCode == RESULT_CANCELED) return@ActivityResultCallback

        imgUri = it.data?.data!!
        Glide.with(this).load(imgUri).into(binding.iv)

    })
}