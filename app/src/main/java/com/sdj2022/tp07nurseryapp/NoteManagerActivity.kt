package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.sdj2022.tp07nurseryapp.databinding.ActivityNoteManagerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class NoteManagerActivity : AppCompatActivity() {

    val binding by lazy { ActivityNoteManagerBinding.inflate(layoutInflater) }

    var items = mutableListOf<NoteItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.tvDate.text = "${GUserData.userData["nursery"]} - "+SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        binding.fab.setOnClickListener{startActivity(Intent(this, AddNoteActivity::class.java))}

        binding.spinnerChildren.dropDownVerticalOffset = 100
        // firestore db에서 원아목록 가져오기
        val children = mutableListOf<String>()

        val db = FirebaseFirestore.getInstance()
        db.collection("account").whereNotEqualTo("birth", null).get().addOnSuccessListener {
            children.add(0,"전체")
            for(document in it.documents){
                if(document["nursery"] == GUserData.userData["nursery"])children.add("${document["name"]}(${document["birth"]})")
            }
            binding.spinnerChildren.adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, children)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
            binding.swipeRefreshLayout.isRefreshing = false
            Snackbar.make(binding.root,"${binding.spinnerChildren.selectedItem} 조회", Snackbar.LENGTH_SHORT).show()
        }

        binding.btnSearch.setOnClickListener {
            binding.recycler.scrollToPosition(0)
            Snackbar.make(binding.root,"${binding.spinnerChildren.selectedItem} 조회", Snackbar.LENGTH_SHORT).show()
            loadData() }

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
        retrofitService.getNoteData(binding.spinnerChildren.selectedItem.toString().split("(")[0], GUserData.userData["nursery"]).enqueue(object : Callback<MutableList<NoteItem>>{
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
                Toast.makeText(this@NoteManagerActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}