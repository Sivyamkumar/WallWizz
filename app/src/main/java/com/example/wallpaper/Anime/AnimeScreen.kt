package com.example.wallpaper.Anime

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.wallpaper.test

lateinit var listofanimeItem : List<AnimeItem>

@Composable
fun AnimeScreen(){
    val AIGeneratedimages : AnimeViewModel = viewModel()
    val viewstate by AIGeneratedimages.LoadingState

    Box(modifier = Modifier.fillMaxSize()){
        when{
            viewstate.loading ->{
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            viewstate.error != null->{
                Text(text = "ERROR OCCURRED \n ${viewstate.error}")
                Log.e("View State.error -> " , viewstate.error!!)
            }else ->{
                listofanimeItem = viewstate.list
                Anime(viewstate.list)
            }
        }
    }
}

@Composable
fun getAnime():List<AnimeItem>{
    return try{
        listofanimeItem
    }catch (e:Exception){
        Log.e("Get Anime", "Error in getting Anime: ${e.message}")
        return emptyList()
    }
}

@Composable
fun Anime(Images : List<AnimeItem>){
    LazyVerticalGrid(GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
        items(Images) { Image ->
            AnimeItems(Images = Image)
        }
    }
}

@Composable
fun AnimeItems(Images: AnimeItem){
    var showDetail by remember{mutableStateOf(false)}

    Button(onClick = {
        showDetail = true
    },colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
        Image(
            painter = rememberAsyncImagePainter(model = Images.imageUrl),
            contentDescription = "",
            modifier = Modifier.size(280.dp,250.dp)
        )
    }

    val context = LocalContext.current

    if(showDetail){
        val intent = Intent(context, test::class.java)
        intent.putExtra("ImageUrl",Images.imageUrl)
        context.startActivity(intent)
    }
}
