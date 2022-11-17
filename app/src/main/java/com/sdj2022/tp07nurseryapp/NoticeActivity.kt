package com.sdj2022.tp07nurseryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.snackbar.Snackbar
import com.sdj2022.tp07nurseryapp.databinding.ActivityNoticeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class NoticeActivity : AppCompatActivity() {

    val binding by lazy { ActivityNoticeBinding.inflate(layoutInflater) }

    var items = mutableListOf<NoticeItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        binding.tvDate.text = "${GUserData.userData["nursery"]} - "+SimpleDateFormat("yyyy년 MM월 dd일").format(Date())

        loadData()

        // 계정에 따라 추가버튼 유무..
        if(GUserData.userData["position"].equals("0")) binding.fab.visibility = View.GONE
        binding.fab.setOnClickListener{startActivity(Intent(this, AddNoticeActivity::class.java))}


        // 더미데이터
//        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
//        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
//        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
//        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
//        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))
//        items.add(NoticeItem("https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "김개똥 원장", "2022년10월12일","https://firebasestorage.googleapis.com/v0/b/tp07nurseryapp.appspot.com/o/profile%2FIMG_20221115014712.png?alt=media&token=a271785e-36da-472f-8000-8a8de40e8c3c", "안녕하세요", "만나서 반갑습니다 테스트용 글작성 중 입니다. 감사합니다."))

        binding.recycler.adapter = NoticeAdapter(this, items)

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
            binding.swipeRefreshLayout.isRefreshing = false
            Snackbar.make(binding.root,"새로고침 되었습니다",Snackbar.LENGTH_SHORT).show()
        }
    }//onCreate

    fun loadData(){
        val retrofit = RetrofitHelper().getRetrofitInstance("http://dj2022.dothome.co.kr")
        val retrofitService = retrofit.create(AddRetrofitService::class.java)
        retrofitService.getNoticeData(GUserData.userData["nursery"]).enqueue(object : Callback<MutableList<NoticeItem>>{
            override fun onResponse(
                call: Call<MutableList<NoticeItem>>,
                response: Response<MutableList<NoticeItem>>
            ) {
                items.clear()
                binding.recycler.adapter?.notifyDataSetChanged()
                var list = response.body()!!

                for (item in list){
                    items.add(0, item)
                    binding.recycler.adapter?.notifyItemInserted(0)
                }
            }

            override fun onFailure(call: Call<MutableList<NoticeItem>>, t: Throwable) {
                Toast.makeText(this@NoticeActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}