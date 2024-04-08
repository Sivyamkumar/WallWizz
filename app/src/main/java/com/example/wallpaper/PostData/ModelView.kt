package com.example.wallpaper.PostData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.AIGenerated.AIGeneratedService
import com.example.wallpaper.Anime.AnimeService
import com.example.wallpaper.Images.Service
import com.example.wallpaper.Motivation.MotivationService
import com.example.wallpaper.Nature.NatureService
import com.example.wallpaper.SuperHero.SuperHeroService
import com.example.wallpaper.Trending.TrendingService
import kotlinx.coroutines.launch

class ModelView:ViewModel() {

    fun postAIGeneratedImages(data: InputData){
        viewModelScope.launch {
            AIGeneratedService.PostAIGeneratedImage(data)
        }
    }

    fun postAnimeData(data: InputData) {
        viewModelScope.launch {
             AnimeService.PostAnimeImage(data)
        }
    }

    fun postMiscellaneousData(data: InputData){
        viewModelScope.launch {
            Service.PostmiscellaneousImage(data)
        }
    }

    fun postMotivationData(data: InputData){
        viewModelScope.launch {
            MotivationService.postMotivationImage(data)
        }
    }

    fun postNatureData(data: InputData){
        viewModelScope.launch {
            NatureService.PostNatureImage(data)
        }
    }

    fun postSuperHeroData(data: InputData){
        viewModelScope.launch {
            SuperHeroService.PostSuperHeroImage(data)
        }
    }

    fun postTrendingData(data: InputData){
        viewModelScope.launch {
            TrendingService.PosttrendingImage(data)
        }
    }



}