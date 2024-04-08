package com.example.wallpaper.Images

data class MiscellaneousItem(
    val author: String,
    val genre: String,
    val id: Int,
    val imageUrl: String
)

data class Miscellaneous (val miscellaneous: ArrayList<MiscellaneousItem>)