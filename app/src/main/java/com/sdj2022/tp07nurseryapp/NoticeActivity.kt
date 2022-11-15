package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sdj2022.tp07nurseryapp.databinding.ActivityNoticeBinding
import java.text.SimpleDateFormat
import java.util.*

class NoticeActivity : AppCompatActivity() {

    val binding by lazy { ActivityNoticeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.tvDate.text = SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        // 계정에 따라 추가버튼 유무..
        if(GUserData.userData["position"].equals("0")) binding.fab.visibility = View.GONE
        binding.fab.setOnClickListener{startActivity(Intent(this, AddNoticeActivity::class.java))}

        var items = mutableListOf<NoticeItem>()
        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))

        binding.recycler.adapter = NoticeAdapter(this, items)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}