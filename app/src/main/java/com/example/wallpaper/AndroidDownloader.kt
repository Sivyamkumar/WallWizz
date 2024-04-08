package com.example.wallpaper

import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.net.toUri

class AndroidDownloader(context: Context) : Downloader {

    @RequiresApi(Build.VERSION_CODES.M)
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun downloadFile(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setMimeType("image/jpeg,image/jpg,image/png,image/webp")
            .setTitle("Image Downloaded")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Image.jpg")
        return downloadManager.enqueue(request)
    }
}