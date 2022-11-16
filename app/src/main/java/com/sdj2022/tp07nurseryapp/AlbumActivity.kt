package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sdj2022.tp07nurseryapp.databinding.ActivityAlbumBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AlbumActivity : AppCompatActivity() {

    val binding by lazy {ActivityAlbumBinding.inflate(layoutInflater)}

    var items = mutableListOf<AlbumItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.tvDate.text = "${GUserData.userData["nursery"]} - "+SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        if(GUserData.userData["position"].equals("0")) binding.fab.visibility = View.GONE
        binding.fab.setOnClickListener{startActivity(Intent(this, AddAlbumActivity::class.java))}

        loadData()

        // 더미데이터
//        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
//        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
//        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
//        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
//        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
//        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))
//        items.add(AlbumItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "앨범의 제목입니다..", "2022년10월12일"))

        binding.recycler.adapter = AlbumAdapter(this, items)

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    fun loadData(){
        val retrofit = RetrofitHelper().getRetrofitInstance("http://dj2022.dothome.co.kr")
        val retrofitService = retrofit.create(AddRetrofitService::class.java)
        retrofitService.getAlbumData(GUserData.userData["nursery"]).enqueue(object :
            Callback<MutableList<AlbumItem>> {
            override fun onResponse(
                call: Call<MutableList<AlbumItem>>,
                response: Response<MutableList<AlbumItem>>
            ) {
                items.clear()
                binding.recycler.adapter?.notifyDataSetChanged()
                var list = response.body()!!

                for (item in list){
                    items.add(0, item)
                    binding.recycler.adapter?.notifyItemInserted(0)
                }
            }

            override fun onFailure(call: Call<MutableList<AlbumItem>>, t: Throwable) {
                Toast.makeText(this@AlbumActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}