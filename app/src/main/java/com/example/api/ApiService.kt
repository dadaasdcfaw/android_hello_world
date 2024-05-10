package com.example.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
//    @GET("app")
////    fun getMethod(@Path("id") postId: Int): Call<random_app_payload>
//    fun getMethod(): Call<String>

    @POST("app")
    fun postMethod(@Body requestData: random_app_payload): Call<String>
}