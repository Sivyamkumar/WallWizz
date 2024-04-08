package com.example.wallpaper.Anime

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

val AnimeService = retrofit.create(AnimeAPI::class.java)

interface AnimeAPI {

    @GET("/anime")
    suspend fun fetchAnimeImage():ArrayList<AnimeItem>

    @POST("/anime")
    suspend fun PostAnimeImage(@Body jsonData: InputData): Response<response>

}