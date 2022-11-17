package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.sdj2022.tp07nurseryapp.databinding.ActivityNoteParentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class NoteParentActivity : AppCompatActivity() {

    val binding by lazy {ActivityNoteParentBinding.inflate(layoutInflater)}

    var items = mutableListOf<NoteItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.tvName.text = "${GUserData.userData["name"]} 어린이"

        binding.tvDate.text = "${GUserData.userData["nursery"]} - "+SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        loadData()

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
            binding.swipeRefreshLayout.isRefreshing = false
            Snackbar.make(binding.root,"${GUserData.userData["name"]} 어린이 조회", Snackbar.LENGTH_SHORT).show()
        }


        // 더미데이터
//        items.add(NoteItem("To 홍길동 학부모님","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
//        items.add(NoteItem("To 홍길동 학부모님","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
//        items.add(NoteItem("To 홍길동 학부모님","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
//        items.add(NoteItem("To 홍길동 학부모님","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
//        items.add(NoteItem("To 홍길동 학부모님","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
//        items.add(NoteItem("To 홍길동 학부모님","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))

        binding.recycler.adapter = NoteAdapter(this, items)
    }

    fun loadData(){
        val retrofit = RetrofitHelper().getRetrofitInstance("http://dj2022.dothome.co.kr")
        val retrofitService = retrofit.create(AddRetrofitService::class.java)
        retrofitService.getNoteData(GUserData.userData["name"]!!, GUserData.userData["nursery"]).enqueue(object :
            Callback<MutableList<NoteItem>> {
            override fun onResponse(
                call: Call<MutableList<NoteItem>>,
                response: Response<MutableList<NoteItem>>
            ) {
                items.clear()
                binding.recycler.adapter?.notifyDataSetChanged()

                var list = response.body()!!
                for (item in list){
                    items.add(0,item)
                    binding.recycler.adapter?.notifyItemInserted(0)
                }
            }

            override fun onFailure(call: Call<MutableList<NoteItem>>, t: Throwable) {
                Toast.makeText(this@NoteParentActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}