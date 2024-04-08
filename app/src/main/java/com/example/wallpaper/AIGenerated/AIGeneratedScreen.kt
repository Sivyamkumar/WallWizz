package com.example.wallpaper.AIGenerated

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.wallpaper.R
import com.example.wallpaper.Trending.listoftrendingitems
import com.example.wallpaper.Trending.trendingItem
import com.example.wallpaper.test
import com.example.wallpaper.ui.theme.Signikafont

lateinit var listofaigenerateditem:List<AIGeneratedItem>

@Composable
fun AIGeneratedScreen(){
    val AIGeneratedimages : AIGeneratedViewModel = viewModel()
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
                listofaigenerateditem = viewstate.list
                AIGenerated(viewstate.list)
            }
        }
    }
}


@Composable
fun getAIgenerated():List<AIGeneratedItem>{
    return try {
        listofaigenerateditem
    } catch (e: Exception) {
        Log.e("GetAIGenerated", "Error in getting AI generated: ${e.message}")
        emptyList()
    }
}

@Composable
fun AIGenerated(Images : List<AIGeneratedItem>){
    LazyVerticalGrid(GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
        items(Images) { Image ->
            AIGeneratedItems(Images = Image)
        }
    }
}

@Composable
fun AIGeneratedItems(Images: AIGeneratedItem){
    var showDetail by remember{ mutableStateOf(false) }

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

@Composable
fun BottomBar() {
    val context = LocalContext.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(id = R.color.Light_Gray))){
        Box(modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .padding(start = 30.dp, top = 10.dp, bottom = 10.dp)
            .background(Color.White, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .padding(start = 10.dp, top = 7.dp, end = 10.dp, bottom = 10.dp)
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(50.dp)),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {

            },colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                Text(
                    text = "Set as Wallpaper",
                    color = Color.Black,
                    fontFamily = Signikafont,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 15.dp)
                        .height(50.dp)
                )
            }
        }
    }
}
