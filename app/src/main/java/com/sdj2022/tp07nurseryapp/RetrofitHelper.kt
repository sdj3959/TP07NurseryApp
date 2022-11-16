package com.sdj2022.tp07nurseryapp

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitHelper {

    //Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $ 에러
    var gson = GsonBuilder().setLenient().create()

    fun getRetrofitInstance(baseUrl:String): Retrofit{
        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit

    }

}