package com.example.wallpaper.Images

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

val Service = retrofit.create(API::class.java)

interface API {

    @GET("/images")
    suspend fun fetchMiscellaneousImages():ArrayList<MiscellaneousItem>


    @POST("/images")
    suspend fun PostmiscellaneousImage(@Body jsonData: InputData): Response<response>

}

//                  FUNCTION TO CHECK SUCCESSFUL OR UNSUCCESSFUL CONNECTION
//suspend fun main(){
//    try{
//        val response = Service.fetchMiscellaneousImages()
//        for( x in response){
//            println("Image: ${x.imageUrl} \nGenre: ${x.genre} \n Author: ${x.author}")
//            println("")
//        }
//    }catch (e:Error){
//        println(e.message)
//    }
//}