package com.sdj2022.tp07nurseryapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TripRetrofitService {

    @GET("areaBasedList?_type=json&MobileOS=AND&MobileAPP=유아노트&listYN=Y&arrange=C")
    fun getDataToString(@Query("serviceKey") key:String,
                        @Query("numOfRows") numOfRows:Int,
                        @Query("pageNo") pageNo:Int,
                        @Query("areaCode") areaCode:Int,
                        @Query("sigunguCode") sigunguCode:Int
    ) : Call<String>


//    @Query("MobileOS") mobileOs:String = "AND",
//    @Query("MobileApp") mobileApp:String = "유아노트",
//    @Query("_type") type:String = "json",
//    @Query("listYN") listYN:String = "Y",
//    @Query("arrange") arrange:String = "C",
}