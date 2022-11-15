package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sdj2022.tp07nurseryapp.databinding.ActivityAlbumBinding
import java.text.SimpleDateFormat
import java.util.*

class AlbumActivity : AppCompatActivity() {

    val binding by lazy {ActivityAlbumBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.tvDate.text = SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        if(GUserData.userData["position"].equals("0")) binding.fab.visibility = View.GONE
        binding.fab.setOnClickListener{startActivity(Intent(this, AddAlbumActivity::class.java))}


        var items = mutableListOf<AlbumItem>()
        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))

        binding.recycler.adapter = AlbumAdapter(this, items)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}