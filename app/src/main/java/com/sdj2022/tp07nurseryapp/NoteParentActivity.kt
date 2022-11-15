package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdj2022.tp07nurseryapp.databinding.ActivityNoteParentBinding
import java.text.SimpleDateFormat
import java.util.*

class NoteParentActivity : AppCompatActivity() {

    val binding by lazy {ActivityNoteParentBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.tvName.text = "${GUserData.userData["name"]} 어린이"

        binding.tvDate.text = SimpleDateFormat("yyyy년 MM월 dd일").format(Date())


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