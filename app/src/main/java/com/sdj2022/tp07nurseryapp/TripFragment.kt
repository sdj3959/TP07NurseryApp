package com.sdj2022.tp07nurseryapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sdj2022.tp07nurseryapp.databinding.FragmentTripBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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
        loadTripData()
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

        val retrofitService = retrofit.create(TripRetrofitService::class.java)
        val call = retrofitService.getData(serviceKey,5,1,"어린이","application/json")
        call.enqueue(object:Callback<TripItemResponse>{
            override fun onResponse(call: Call<TripItemResponse>, response: Response<TripItemResponse>) {
                val apiResponse = response.body()

//                apiResponse?.items?.let { binding.recycler.adapter =
//                    activity?.let { activity -> TripAdapter(activity, it) }
//                }

                apiResponse?.item?.let { binding.recycler.adapter = TripAdapter(activity!!, it) }
                Toast.makeText(activity, "성공? ${apiResponse?.item?.size}", Toast.LENGTH_SHORT).show()

                //AlertDialog.Builder(activity).setMessage().show()
            }

            override fun onFailure(call: Call<TripItemResponse>, t: Throwable) {
                Toast.makeText(activity, "실패!", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(activity).setMessage(t.message).show()
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