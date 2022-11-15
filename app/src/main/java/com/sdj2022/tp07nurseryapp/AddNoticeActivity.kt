package com.sdj2022.tp07nurseryapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.sdj2022.tp07nurseryapp.databinding.ActivityAddNoticeBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddNoticeActivity : AppCompatActivity() {

    val binding by lazy { ActivityAddNoticeBinding.inflate(layoutInflater) }

    var imgPath:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.addPic.setOnClickListener{ clickPic() }
        binding.btnComplete.setOnClickListener { clickComplete() }
    }

    fun clickComplete(){
        var title = binding.etTitle.text.toString()
        var msg = binding.etMessage.text.toString()

        val retrofit = RetrofitHelper.getRetrofitInstance("http://dj2022.dothome.co.kr")
        val retrofitService = retrofit.create(AddRetrofitService::class.java)

        // 이미지 데이터 감싸기
        var filePart:MultipartBody.Part? = null
        if(imgPath != null){
            val file = File(imgPath)
            val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
            filePart = MultipartBody.Part.createFormData("img", file.name, requestBody)
        }

        // 글 데이터 감싸기
        val dataPart = hashMapOf<String, String>()
        dataPart.put("title", title)
        dataPart.put("msg", msg)

        // 데이터 보내기
        retrofitService.noticeData(dataPart, filePart).enqueue()
    }

    fun clickPic(){
        val intent = Intent(Intent.ACTION_PICK).setType("image/*")
        activityResultLauncher.launch(intent)
    }

    var activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
        if(it.resultCode == RESULT_CANCELED) return@ActivityResultCallback
        imgPath = getRealPathFromUri(it.data?.data)
        Glide.with(this).load(it.data?.data).into(binding.iv)
    })

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    fun getRealPathFromUri(uri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(
            this,
            uri!!, proj, null, null, null
        )
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result
    }
}