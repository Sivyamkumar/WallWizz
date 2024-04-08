package com.example.wallpaper

interface Downloader {
    fun downloadFile(url: String): Long
}