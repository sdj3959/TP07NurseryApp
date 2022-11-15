package com.sdj2022.tp07nurseryapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sdj2022.tp07nurseryapp.databinding.FragmentTripBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.random.Random

class TripFragment:Fragment() {

    private var _binding: FragmentTripBinding? = null
    val binding get() = _binding!!

    //var apiResponse: TripItemResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTripBinding.inflate(inflater, container, false)

        binding.btnSearch.setOnClickListener{

            if(binding.etSearch.text.toString() != ""){
                binding.progressBar.visibility = View.VISIBLE

                // 검색 후 키보드 숨김처리
                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)

                // json 파싱 후 아이템들 리사이클러뷰에 배치
                loadTripData()
            }else{
                AlertDialog.Builder(activity).setMessage("검색어를 입력하세요").setPositiveButton("확인", object : OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                    }
                }).show()
            }
        }
        return binding.root
        //return FragmentTripBinding.inflate(inflater, container, false).root
        //return inflater.inflate(R.layout.fragment_trip, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun loadTripData(){
        val serviceKey = "fb37113b-1a86-452a-a57b-c83f6df97d26"

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.kcisa.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // api 결과 페이지 랜덤값
        //var rndInt = Random.nextInt(5000)+4

        val retrofitService = retrofit.create(TripRetrofitService::class.java)

        val call = retrofitService.getData(serviceKey,5, binding.etSearch.text.toString(),"application/json")
        call.enqueue(object:Callback<ApiResponse>{
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                var apiResponse:ApiResponse? = response.body()

                // 중복된 아이템 삭제..
//                var t = 5
//                for(i in 1 until  t){
//                    if(apiResponse?.response?.body?.items?.item?.get(i-1)?.title.equals(apiResponse?.response?.body?.items?.item?.get(i)?.title))
//                    {
//                        apiResponse?.response?.body?.items?.item?.removeAt(i-1)
//                        t--
//                    }
//                }

                binding.progressBar.visibility = View.GONE

                if(apiResponse?.response?.body?.items?.item?.size != 0) apiResponse?.response?.body?.items?.item?.let {
                    binding.recycler.adapter = TripAdapter(activity!!, it)
                }
                else{

                    binding.tvNoSearch.visibility = View.VISIBLE
                }


                //Toast.makeText(activity, "성공! ${apiResponse?.response?.body?.items?.item?.size}", Toast.LENGTH_SHORT).show()

                //AlertDialog.Builder(activity).setMessage().show()
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(activity, "잠시 후 다시 시도하세요.", Toast.LENGTH_SHORT).show()
            }
        })
        
        
//        retrofitService.getDataToString(serviceKey, 3, 1, "어린이", "application/json")
//            .enqueue(object : Callback<String>{
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    val s = response.body()
//                    AlertDialog.Builder(activity).setMessage(s).show()
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    Toast.makeText(activity, "실패!", Toast.LENGTH_SHORT).show()
//                }
//            })
    }
}