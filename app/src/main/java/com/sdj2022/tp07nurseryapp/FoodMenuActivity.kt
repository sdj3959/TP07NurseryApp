package com.sdj2022.tp07nurseryapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sdj2022.tp07nurseryapp.databinding.ActivityFoodMenuBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FoodMenuActivity : AppCompatActivity() {

    val binding by lazy {ActivityFoodMenuBinding.inflate(layoutInflater)}

    lateinit var imgUri:Uri
    var imgPath:String? = null

    lateinit var imgUrl:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        loadData()

        binding.tvDate.text = "${GUserData.userData["nursery"]} - "+SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        if(GUserData.userData["position"].equals("0")) binding.fab.visibility = View.GONE
        binding.fab.setOnClickListener{
//            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var intent = Intent(Intent.ACTION_PICK).setType("image/*")
            resultLauncher.launch(intent)
        }

        binding.iv.setOnClickListener{
            var intent = Intent(this, PhotoActivity::class.java)
            intent.putExtra("img",imgUrl)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
        if (it.resultCode == RESULT_CANCELED) return@ActivityResultCallback

        imgPath = getRealPathFromUri(it.data?.data)
        imgUri = it.data?.data!!
        Glide.with(this).load(imgUri).into(binding.iv)

        // 기존 데이터 삭제후 추가
        val retrofit = RetrofitHelper().getRetrofitInstance("http://dj2022.dothome.co.kr")
        val retrofitService = retrofit.create(AddRetrofitService::class.java)

        val dataPart = hashMapOf<String, String?>()
        dataPart.put("nursery",GUserData.userData["nursery"])

        val file = File(imgPath)
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        var filePart = MultipartBody.Part.createFormData("img", file.name, requestBody)

        retrofitService.foodmenuData(dataPart, filePart).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                //Toast.makeText(this@FoodMenuActivity, response.body(), Toast.LENGTH_SHORT).show()
                Snackbar.make(binding.root,response.body().toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@FoodMenuActivity, "등록에 실패하였습니다. 잠시후 다시 시도 해주세요", Toast.LENGTH_SHORT).show()
            }

        })

    })

    fun loadData(){
        val retrofit = RetrofitHelper().getRetrofitInstance("http://dj2022.dothome.co.kr")
        val retrofitService = retrofit.create(AddRetrofitService::class.java)
        retrofitService.getFoodmenuData(GUserData.userData["nursery"]).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                imgUrl = "http://dj2022.dothome.co.kr/NurseryApp/"+response.body()
                Glide.with(this@FoodMenuActivity).load(imgUrl).into(binding.iv)
                Snackbar.make(binding.root,"식단표를 불러왔습니다", Snackbar.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@FoodMenuActivity, "식단표를 불러오는데 실패하였습니다. 잠시후 다시 시도 해주세요", Toast.LENGTH_SHORT).show()
            }
        })
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