package com.sdj2022.tp07nurseryapp

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.sdj2022.tp07nurseryapp.databinding.ActivityAddNoteBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddNoteActivity : AppCompatActivity() {

    val binding by lazy { ActivityAddNoteBinding.inflate(layoutInflater) }

    var imgPath:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.addPic.setOnClickListener { clickPic() }

        binding.spinner.dropDownVerticalOffset = 120
        // firestore db에서 원아목록 가져오기
        val children = mutableListOf<String>()

        val db = FirebaseFirestore.getInstance()
        db.collection("account").whereNotEqualTo("birth", null).get().addOnSuccessListener {
            for(document in it.documents){
                if(document["nursery"] == GUserData.userData["nursery"])children.add("${document["name"]}(${document["birth"]})")
            }
            binding.spinner.adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, children)
        }
        
        
        binding.btnComplete.setOnClickListener { clickComplete() }
    }
    
    fun clickComplete(){
        if(binding.etMessage.length()<1) {
            Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        binding.btnComplete.isEnabled = false

        var name = binding.spinner.selectedItem.toString().split("(")[0]
        var msg = binding.etMessage.text.toString()

        val retrofit = RetrofitHelper().getRetrofitInstance("http://dj2022.dothome.co.kr")
        val retrofitService = retrofit.create(AddRetrofitService::class.java)

        var filePart : MultipartBody.Part? = null
        if(imgPath != null){
            var file = File(imgPath)
            var requestBody = RequestBody.create(MediaType.parse("image/*"), file)
            filePart = MultipartBody.Part.createFormData("img", file.name, requestBody)
        }

        var dataPart = hashMapOf<String,String?>()
        dataPart.put("name", name)
        dataPart.put("msg", msg)
        dataPart.put("nursery",GUserData.userData["nursery"])

        retrofitService.noteData(dataPart,filePart).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Toast.makeText(this@AddNoteActivity, response.body(), Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@AddNoteActivity, "등록에 실패했습니다. 잠시후 다시 시도 해주세요", Toast.LENGTH_SHORT).show()
                binding.btnComplete.isEnabled = true
            }
        })
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

    override fun onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder(this).setTitle("유아노트").setMessage("작성을 취소하시겠습니까?").setPositiveButton("확인",
            DialogInterface.OnClickListener { dialogInterface, i ->
                finish()
            }).setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->  }).show()
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