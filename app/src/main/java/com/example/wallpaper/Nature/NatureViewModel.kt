package com.example.wallpaper.Nature

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.AIGenerated.AIGeneratedItem
import kotlinx.coroutines.launch

class NatureViewModel:ViewModel() {

    private val _LoadingState = mutableStateOf(Loading())
    val LoadingState : State<Loading> = _LoadingState

    init {
        fetchImages()
    }

    private fun fetchImages(){
        viewModelScope.launch {
            try{
                val response = NatureService.fetchNatureImage()
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
        val list: ArrayList<NatureItem> = arrayListOf(),
        val error: String? = null
    )

}