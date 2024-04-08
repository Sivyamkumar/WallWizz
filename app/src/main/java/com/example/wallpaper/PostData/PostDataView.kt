package com.example.wallpaper.PostData

import android.content.Intent
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaper.MainActivity
import com.example.wallpaper.R
import com.example.wallpaper.SetWallpaper
import com.example.wallpaper.ui.theme.Chivo
import com.example.wallpaper.ui.theme.playfair
import kotlinx.coroutines.launch

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null
)

data class dropDownItem(
    val title: String
)


@Composable
fun MainScreen(viewModel : ModelView){
    Header(viewModel)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(viewModel: ModelView){
    val context = LocalContext.current

    val items = listOf(
        NavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            title = "Post an Image",
            selectedIcon = Icons.Filled.Edit,
            unselectedIcon = Icons.Outlined.Edit,
        ),
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedItemIndex by rememberSaveable {mutableStateOf(0)}

    Column(modifier = Modifier.fillMaxSize()) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(20.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                androidx.compose.material3.Text(
                                    item.title,
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                )
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                if(item.title == "Home"){
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                }
                                scope.launch {
                                    drawerState.close()
                                }
                            },icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("WallWizz",
                                fontFamily = playfair
                            )
                        },
                        colors = TopAppBarColors(
                            containerColor = colorResource(id = R.color.Light_Blue),
                            navigationIconContentColor = Color.White,
                            titleContentColor = Color.White,
                            actionIconContentColor = Color.White,
                            scrolledContainerColor = Color.Transparent,
                        ),
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(Icons.Default.Menu, "Menu Button")
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                       val intent = Intent(context, MainActivity::class.java)
                                       context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Home Screen",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            ) { paddingValue ->
                Column(modifier = Modifier.padding(paddingValues = paddingValue)) {
                    topbar()

                    Spacer(modifier = Modifier.padding(top = 40.dp))

                    ContentSection(viewModel)
                }
            }
        }
    }
}

@Composable
fun topbar(){
    Row(modifier= Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(top = 30.dp, start = 90.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            "WallWizz",
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 70.dp),
            fontFamily = playfair
        )
    }
}

@Composable
fun ContentSection(viewModel: ModelView){

    var imageurl by remember { mutableStateOf("") }
    var imagegenre by remember { mutableStateOf("") }
    var imageauthor by remember { mutableStateOf("") }
    var clicked by remember{ mutableStateOf(false) }
    var showdialogbox by remember{ mutableStateOf(false) }

    val context = LocalContext.current
    val genreItem = listOf("Miscellaneous","Trending","AI Generated","SuperHero","Anime","Nature","Motivation")

    val dropdownitem = listOf(
        dropDownItem(
            "Miscellaneous"
        ),
        dropDownItem(
            "Trending"
        ),
        dropDownItem(
            "AI Generated"
        ),
        dropDownItem(
            "SuperHero"
        ),
        dropDownItem(
            "Anime"
        ),
        dropDownItem(
            "Nature"
        ),
        dropDownItem(
            "Motivation"
        )
    )

    Column(modifier = Modifier
        .wrapContentSize()
        .padding(start = 50.dp)) {
        OutlinedTextField(
            value = imageurl,
            onValueChange = {
                imageurl = it
            },
            label = {
                Text(text = "Enter image url" , color = Color.Black, fontSize = 16.sp)
            },
            modifier = Modifier
                .height(60.dp)
                .width(300.dp)
        )

        Spacer(modifier = Modifier.padding(top = 40.dp))

        Row(modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            OutlinedTextField(
                value = imagegenre,
                onValueChange = {
                    imagegenre = it
                },
                label = {
                    Text("Select genre of your Image", color = Color.Black, fontSize = 16.sp)
                },
                modifier = Modifier
                    .height(60.dp)
                    .width(300.dp)
            )
            Column{
                IconButton(
                    onClick = {
                        clicked=true
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Genre Option",tint = Color.Black)
                }
                if(clicked){
                    DropdownMenu(
                        expanded = clicked, onDismissRequest = { clicked=false }
                    ) {
                        dropdownitem.forEachIndexed{index , item->
                            DropdownMenuItem(onClick = {
                                imagegenre = item.title
                                clicked = false
                            }) {
                                Text(item.title, color = Color.Black, fontSize = 16.sp, fontFamily = Chivo)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(top = 40.dp))

        OutlinedTextField(
            value = imageauthor,
            onValueChange = {
                imageauthor = it
            },
            label = {
                Text("Enter your name", color = Color.Black, fontSize = 16.sp)
            },
            modifier = Modifier
                .height(60.dp)
                .width(300.dp)
        )

        Spacer(modifier = Modifier.padding(top = 40.dp))

    }

    if(showdialogbox){
        AlertDialog(
            onDismissRequest = { showdialogbox=false },
            confirmButton = {
                Button(
                    onClick = {
                        var success : Boolean = false
                        if(URLUtil.isValidUrl(imageurl)){
                            success = SetWallpaper(context,imageurl)
                        }
                        showdialogbox=false
                        if(success){
                            Toast.makeText(context,"Wallpaper changed!!",Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(context,"Error changing wallpaper",Toast.LENGTH_LONG).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.Light_Blue))
                ) {
                    Text("Ok", color = Color.White, fontSize = 16.sp)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showdialogbox=false
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.Light_Blue))
                    ) {
                    Text("Cancel", color = Color.White, fontSize = 16.sp)
                }
            },
            title = {
                Text("Wallpaper?", color = Color.Black, fontSize = 20.sp)
            },
            text = {
                Text("Continue this as wallpaper?", color = Color.Black, fontSize = 16.sp)
            }
        )
    }

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){

        Button(
            onClick = {
                if(!URLUtil.isValidUrl(imageurl)){
                    Toast.makeText(context,"Invalid Url",Toast.LENGTH_LONG).show()
                }else{
                    if(imagegenre.isNullOrEmpty() || imagegenre !in genreItem ){
                        Toast.makeText(context,"Invalid genre", Toast.LENGTH_LONG).show()
                    }else{
                        val data = InputData(
                            imageUrl = imageurl,
                            genre = imagegenre,
                            author = imageauthor
                        )
                        if(imagegenre == "AI Generated"){
                            try{
                                viewModel.postAIGeneratedImages(data)
                                Toast.makeText(context,"Image Posted Succesfully",Toast.LENGTH_LONG).show()
                                showdialogbox=true
                            }catch (e:Exception){
                                Log.e("Data uploading Failed", e.message.toString())
                                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG).show()
                                showdialogbox=true
                            }
                        }
                        else if(imagegenre == "Anime"){
                            try{
                                viewModel.postAnimeData(data)
                                Toast.makeText(context,"Image Posted Succesfully",Toast.LENGTH_LONG).show()
                                showdialogbox=true
                            }catch (e:Exception){
                                Log.e("Data uploading Failed", e.message.toString())
                                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG).show()
                            }
                        }
                        else if(imagegenre == "Miscellaneous"){
                            try{
                                viewModel.postMiscellaneousData(data)
                                Toast.makeText(context,"Image Posted Succesfully",Toast.LENGTH_LONG).show()
                                showdialogbox=true
                            }catch (e:Exception){
                                Log.e("Data uploading Failed", e.message.toString())
                                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG).show()
                            }
                        }
                        else if(imagegenre == "Motivation"){
                            try{
                                viewModel.postMotivationData(data)
                                Toast.makeText(context,"Image Posted Succesfully",Toast.LENGTH_LONG).show()
                                showdialogbox=true
                            }catch (e:Exception){
                                Log.e("Data uploading Failed", e.message.toString())
                                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG).show()
                            }
                        }else if(imagegenre == "Nature"){
                            try{
                                viewModel.postNatureData(data)
                                Toast.makeText(context,"Image Posted Succesfully",Toast.LENGTH_LONG).show()
                                showdialogbox=true
                            }catch (e:Exception){
                                Log.e("Data uploading Failed", e.message.toString())
                                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG).show()
                            }
                        }
                        else if(imagegenre == "SuperHero"){
                            try{
                                viewModel.postSuperHeroData(data)
                                Toast.makeText(context,"Image Posted Succesfully",Toast.LENGTH_LONG).show()
                                showdialogbox=true
                            }catch (e:Exception){
                                Log.e("Data uploading Failed", e.message.toString())
                                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG).show()
                            }
                        }else if(imagegenre == "Trending"){
                            try{
                                viewModel.postTrendingData(data)
                                Toast.makeText(context,"Image Posted Succesfully",Toast.LENGTH_LONG).show()
                                showdialogbox=true
                            }catch (e:Exception){
                                Log.e("Data uploading Failed", e.message.toString())
                                Toast.makeText(context,"Something went wrong.",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.Light_Blue))
        ) {
            Text("Post", fontSize = 24.sp, color = Color.White)
        }
    }
}
