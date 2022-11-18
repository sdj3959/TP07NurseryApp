package com.sdj2022.tp07nurseryapp

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface TripRetrofitService {

    // 관광지정보 #######
    @GET("/openapi/service/rest/convergence/conver8")
    fun getData(@Query("serviceKey") key:String,
                        @Query("numOfRows") numOfRows:Int,
                        @Query("keyword") keyword:String,
                        @Header("Accept") form:String = "application/json"
    ) : Call<ApiResponse>

    @GET("/openapi/service/rest/convergence/conver8")
    fun getDataToString(@Query("serviceKey") key:String,
                @Query("numOfRows") numOfRows:Int,
                @Query("pageNo") pageNo:Int,
                @Query("keyword") keyword:String,
                @Header("Accept") form:String = "application/json"
    ) : Call<String>


}