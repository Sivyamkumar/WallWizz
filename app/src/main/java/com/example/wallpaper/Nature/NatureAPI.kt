package com.example.wallpaper.Nature

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

val NatureService = retrofit.create(NatureAPI::class.java)

interface NatureAPI {

    @GET("/nature")
    suspend fun fetchNatureImage():ArrayList<NatureItem>

    @POST("/nature")
    suspend fun PostNatureImage(@Body jsonData: InputData): Response<response>
}