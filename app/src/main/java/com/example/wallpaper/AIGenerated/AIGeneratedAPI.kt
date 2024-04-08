package com.example.wallpaper.AIGenerated

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

val AIGeneratedService = retrofit.create(AIGeneratedAPI::class.java)

interface AIGeneratedAPI {

    @GET("/ai_generated")
    suspend fun fetchAIGeneratedImages():ArrayList<AIGeneratedItem>


    @POST("/ai_generated")
    suspend fun PostAIGeneratedImage(@Body jsonData: InputData): Response<response>

}