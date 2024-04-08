package com.example.wallpaper.Trending

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

val TrendingService = retrofit.create(TrendingAPI::class.java)

interface TrendingAPI {
    @GET("/trending")
    suspend fun fetchTrendingImage():ArrayList<trendingItem>


    @POST("/trending")
    suspend fun PosttrendingImage(@Body jsonData: InputData): Response<response>
}