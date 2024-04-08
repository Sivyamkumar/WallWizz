package com.example.wallpaper

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class DownloadCompletedReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        context ?: return
        val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1L)
        if(id != -1L){
            println("Download with ID $id finished!")
            Toast.makeText(context,"Image Saved Successfully",Toast.LENGTH_LONG).show()
        }
    }
}