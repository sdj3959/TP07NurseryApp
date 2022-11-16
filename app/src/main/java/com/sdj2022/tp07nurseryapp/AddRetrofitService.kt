package com.sdj2022.tp07nurseryapp

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface AddRetrofitService {

    // 글 작성 및 불러오기 ########

    // 공지사항
    @Multipart
    @POST("NurseryApp/insertNotice.php")
    fun noticeData(
        @PartMap dataPart:HashMap<String,String?>,
        @Part filePart: MultipartBody.Part?
    ): Call<String>

    @GET("NurseryApp/loadNotice.php")
    fun getNoticeData(@Query("nursery") nursery:String?):Call<MutableList<NoticeItem>>


    // 앨범
    @Multipart
    @POST("NurseryApp/insertAlbum.php")
    fun albumData(
        @PartMap dataPart:HashMap<String,String?>,
        @Part filePart:MultipartBody.Part?
    ): Call<String>

    @GET("NurseryApp/loadAlbum.php")
    fun getAlbumData(@Query("nursery") nursery:String?):Call<MutableList<AlbumItem>>

    // 일정표
    @Multipart
    @POST("NurseryApp/insertCalendar.php")
    fun calendarData(
        @PartMap dataPart:HashMap<String,String?>,
        @Part filePart:MultipartBody.Part?
    ): Call<String>

    @GET("NurseryApp/loadCalendar.php")
    fun getCalendarData(@Query("nursery") nursery:String?):Call<String>

    // 식단표
    @Multipart
    @POST("NurseryApp/insertFoodmenu.php")
    fun foodmenuData(
        @PartMap dataPart:HashMap<String,String?>,
        @Part filePart:MultipartBody.Part?
    ): Call<String>

    @GET("NurseryApp/loadFoodmenu.php")
    fun getFoodmenuData(@Query("nursery") nursery:String?):Call<String>

    // 알림장
}