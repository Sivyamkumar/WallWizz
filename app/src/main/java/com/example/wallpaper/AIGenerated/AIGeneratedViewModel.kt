package com.example.wallpaper.AIGenerated

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AIGeneratedViewModel: ViewModel() {

    private val _LoadingState = mutableStateOf(Loading())
    val LoadingState : State<Loading> = _LoadingState

    init {
        fetchImages()
    }

    private fun fetchImages(){
        viewModelScope.launch {
            try{
                val response = AIGeneratedService.fetchAIGeneratedImages()
                _LoadingState.value = _LoadingState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            }catch (e:Exception){
                _LoadingState.value = _LoadingState.value.copy(
                    loading = false,
                    error = "Error Fetching Images ${e.message}"
                )
            }
        }
    }

    data class Loading(
        val loading: Boolean = true,
        val list: ArrayList<AIGeneratedItem> = arrayListOf(),
        val error: String? = null
    )

}