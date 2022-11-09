package com.sdj2022.tp07nurseryapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitHelper {

    companion object{

        fun getRetrofitInstance(baseUrl:String): Retrofit{
            var retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit

        }
    }
}