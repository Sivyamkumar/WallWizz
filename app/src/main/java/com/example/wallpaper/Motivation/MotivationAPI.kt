package com.example.wallpaper.Motivation

import com.example.wallpaper.PostData.InputData
import com.example.wallpaper.PostData.response
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder().baseUrl("https://wallwizzapi.up.railway.app")
    .addConverterFactory(GsonConverterFactory.create()).build()

val MotivationService = retrofit.create(MotivationAPI::class.java)

interface MotivationAPI {

    @GET("/motivation")
    suspend fun fetchMotivationImages():ArrayList<MotivationItem>


    @POST("/motivation")
    suspend fun postMotivationImage(@Body jsonData: InputData): Response<response>

}