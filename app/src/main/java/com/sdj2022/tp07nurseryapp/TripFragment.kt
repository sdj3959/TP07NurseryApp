package com.sdj2022.tp07nurseryapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class TripFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trip, container, false)
        loadTripData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun loadTripData(){
        val serviceKey = "3N5o9gGDyQiQJjpEf70%2BoI2iCs0ocxvkwGUn%2BmEnyn%2FZh%2F3%2BfF7rlU4sOFs8MooNXbstezb4dAZvOpq3LhxTsw%3D%3D"

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/B551011/KorService/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService = retrofit.create(TripRetrofitService::class.java)
        val call = retrofitService.getDataToString(serviceKey,3,1,1,1)
        call.enqueue(object:Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val s:String? = response.body()
                Log.i("MY",s.toString())
                Toast.makeText(activity, "성공?", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(activity).setMessage(s).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(activity, "실패!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}