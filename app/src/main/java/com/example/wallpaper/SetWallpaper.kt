package com.example.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


fun SetWallpaper(context: Context, imageUrl: String) : Boolean{
    Thread {
        val wallpaperManager = WallpaperManager.getInstance(context)
        val bitmap = fetchImageFromUrl(imageUrl)

        if (bitmap != null) {
            try {
                wallpaperManager.setBitmap(bitmap)
                Handler(Looper.getMainLooper()).post {
                    Log.e("Wallpaper Set Scccessfully","")
                }
            } catch (e: Exception) {
                Log.e("Error Setting Wallpaper",e.printStackTrace().toString())
            }
        }
    }.start()
    return true
}

fun fetchImageFromUrl(imageUrl: String): Bitmap? {
    var bitmap: Bitmap? = null
    var inputStream: InputStream? = null
    var connection: HttpURLConnection? = null
    try {
        val url = URL(imageUrl)
        connection = url.openConnection() as HttpURLConnection
        connection.connect()
        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            inputStream = connection.inputStream
            bitmap = BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        connection?.disconnect()
        inputStream?.close()
    }
    return bitmap
}