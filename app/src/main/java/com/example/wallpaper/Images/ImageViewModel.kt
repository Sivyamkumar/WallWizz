package com.example.wallpaper.Images

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainviewModel: ViewModel() {

    private val _ImagesState = mutableStateOf(Images())
    val ImageState : State<Images> = _ImagesState

    init {
        fetchImages()
    }

    private fun fetchImages(){
        viewModelScope.launch {
            try{
                val response = Service.fetchMiscellaneousImages()
                _ImagesState.value = _ImagesState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            }catch (e:Exception){
                _ImagesState.value = _ImagesState.value.copy(
                    loading = false,
                    error = "Error Fetching Images ${e.message}"
                )
            }
        }
    }

    data class Images(
        val loading: Boolean = true,
        val list: ArrayList<MiscellaneousItem> = arrayListOf(),
        val error: String? = null
    )

}