package com.example.wallpaper.SuperHero

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

val SuperHeroService = retrofit.create(SuperHeroAPI::class.java)

interface SuperHeroAPI {

    @GET("/superhero")
    suspend fun fetchSuperHeroImages():ArrayList<SuperHeroItem>

    @POST("/superhero")
    suspend fun PostSuperHeroImage(@Body jsonData: InputData): Response<response>

}