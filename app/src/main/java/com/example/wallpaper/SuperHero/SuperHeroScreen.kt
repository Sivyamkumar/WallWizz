package com.example.wallpaper.SuperHero

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
import com.example.wallpaper.Trending.listoftrendingitems
import com.example.wallpaper.Trending.trendingItem
import com.example.wallpaper.test

lateinit var listofsuperheroitem :List<SuperHeroItem>

@Composable
fun SuperHeroScreen(){
    val AIGeneratedimages : SuperHeroViewModel = viewModel()
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
                listofsuperheroitem = viewstate.list
                SuperHero(viewstate.list)
            }
        }
    }
}


@Composable
fun getsuperhero():List<SuperHeroItem>{
    return try {
        listofsuperheroitem
    } catch (e: Exception) {
        Log.e("GetSuperHero", "Error in getting SuperHero: ${e.message}")
        emptyList()
    }
}

@Composable
fun SuperHero(Images : List<SuperHeroItem>){
    LazyVerticalGrid(GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
        items(Images) { Image ->
            SuperHeroItems(Images = Image)
        }
    }
}

@Composable
fun SuperHeroItems(Images: SuperHeroItem){
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
