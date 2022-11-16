package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.sdj2022.tp07nurseryapp.databinding.ActivityNoteManagerBinding
import java.text.SimpleDateFormat
import java.util.Date

class NoteManagerActivity : AppCompatActivity() {

    val binding by lazy { ActivityNoteManagerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.tvDate.text = "${GUserData.userData["nursery"]} - "+SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        binding.fab.setOnClickListener{startActivity(Intent(this, AddNoteActivity::class.java))}

        // firestore db에서 원아목록 가져오기
        val children = mutableListOf<String>()

        val db = FirebaseFirestore.getInstance()
        db.collection("account").whereNotEqualTo("birth", null).get().addOnSuccessListener {
            for(document in it.documents){
                children.add("${document["name"]}(${document["birth"]})")
            }
            binding.spinnerChildren.adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, children)
        }

        binding.btnSearch.setOnClickListener {  }


        var items = mutableListOf<NoteItem>()
        items.add(NoteItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
        items.add(NoteItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
        items.add(NoteItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
        items.add(NoteItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
        items.add(NoteItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))
        items.add(NoteItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "짧막한 설명 글입니다 오늘 재밌게 코딩합니다..","2022년 12월 12일"))

        binding.recycler.adapter = NoteAdapter(this, items)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}