package com.sdj2022.tp07nurseryapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TripRetrofitService {

    @GET("/openapi/service/rest/convergence/conver8")
    fun getData(@Query("serviceKey") key:String,
                        @Query("numOfRows") numOfRows:Int,
                        @Query("pageNo") pageNo:Int,
                        @Query("keyword") keyword:String,
                        @Header("Accept") form:String = "application/json"
    ) : Call<TripItemResponse>

    @GET("/openapi/service/rest/convergence/conver8")
    fun getDataToString(@Query("serviceKey") key:String,
                @Query("numOfRows") numOfRows:Int,
                @Query("pageNo") pageNo:Int,
                @Query("keyword") keyword:String,
                @Header("Accept") form:String = "application/json"
    ) : Call<String>



}