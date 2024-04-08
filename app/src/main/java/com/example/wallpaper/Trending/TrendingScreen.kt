package com.example.wallpaper.Trending

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.wallpaper.test

lateinit var listoftrendingitems : List<trendingItem>

@Composable
fun TrendingScreen(){
    val AIGeneratedimages : trendingViewModel = viewModel()
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
                listoftrendingitems = viewstate.list
                Trend(viewstate.list)
            }
        }
    }
}

@Composable
fun getTrends():List<trendingItem>{
    return try {
        listoftrendingitems
    } catch (e: Exception) {
        Log.e("getTrends", "Error in getTrends: ${e.message}")
        emptyList()
    }
}

@Composable
fun Trend(Images : List<trendingItem>){

    LazyVerticalGrid(GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
        items(Images) { Image ->
            TrendingItems(Images = Image)
        }
    }
}

@Composable
fun TrendingItems(Images: trendingItem){
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

