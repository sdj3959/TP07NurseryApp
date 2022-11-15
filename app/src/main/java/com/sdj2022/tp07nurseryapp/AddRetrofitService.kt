package com.sdj2022.tp07nurseryapp

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface AddRetrofitService {

    // 글 작성 ########
    // 공지사항
    @Multipart
    @POST("NurseryApp/insertNotice.php")
    fun noticeData(
        @PartMap dataPart:HashMap<String,String>,
        @Part filePart: MultipartBody.Part?
    ): Call<String>
}