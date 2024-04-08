package com.example.wallpaper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wallpaper.Images.ChangeWallpaperInstantly
import com.example.wallpaper.PostData.NavigationItem
import com.example.wallpaper.PostData.dropDownItem
import com.example.wallpaper.ui.theme.Acme
import com.example.wallpaper.ui.theme.Chivo
import com.example.wallpaper.ui.theme.Signikafont
import com.example.wallpaper.ui.theme.WallpaperTheme
import com.example.wallpaper.ui.theme.playfair
import kotlinx.coroutines.launch

data class dropDownItem(
    val title: String
)

lateinit var context2 : Context

class test : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        context2 = this@test
        super.onCreate(savedInstanceState)
        setContent {
            WallpaperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val url = intent.getStringExtra("ImageUrl")
                    DetailImage(url)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailImage(Image:String?){

    val context = LocalContext.current

    var changeWallpaperInstantly by remember{mutableStateOf(false)}

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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedItemIndex by rememberSaveable {mutableStateOf(0)}
    var selectedOption by remember{ mutableStateOf("") }

    var showdropdown by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(20.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                    Text(
                                        item.title,
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontFamily = Acme
                                    )
                            },
                            selected = index == selectedItemIndex,
                            onClick = {
                                if(item.title == "Post an Image"){
                                    val intent = Intent(context,MainActivity2::class.java)
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
                                Icon(Icons.Default.Menu,"Menu Button")
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    changeWallpaperInstantly = true
                                } ,
                                modifier = Modifier
                                    .size(50.dp)
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.changewallpaper),
                                    contentDescription = "Change Wallpaper Automatically"
                                )
                            }
                            Column{
                                IconButton(onClick = {
                                    showdropdown=true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Change Wallpaper By Time"
                                    )
                                }

                                if(showdropdown){
                                    Toast.makeText(LocalContext.current,"Select Your Favourite Genre to set wallpaper",
                                        Toast.LENGTH_LONG).show()
                                    DropdownMenu(
                                        expanded = showdropdown,
                                        onDismissRequest = { showdropdown=false }) {
                                        dropdownitem.forEachIndexed { index, item ->
                                            DropdownMenuItem(
                                                onClick = {
                                                    showdropdown=false
                                                    selectedOption = item.title
                                                }
                                            ) {
                                                Text(
                                                    item.title,
                                                    color = Color.Black,
                                                    fontSize = 16.sp,
                                                    fontFamily = Chivo
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    )
                },
                 bottomBar = {
                     BottomAppBar(modifier = Modifier.padding(bottom = 20.dp)) {
                        BottomBar(Image)
                     }
                }
            ) {paddingValue ->
                Column(modifier = Modifier.padding(paddingValues = paddingValue)){
                    Box(modifier = Modifier
                        .height(700.dp)
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                    ){
                        Image(
                            painter = rememberAsyncImagePainter(model = Image),
                            contentDescription = "",
                            modifier = Modifier.size(650.dp)
                        )
                    }
                }
            }
        }
    }

    var successstatus = false
    if(selectedOption == "Miscellaneous"){
        successstatus = ChangeWallpaperInstantly("Miscellaneous")
    }else if(selectedOption == "AI Generated"){
        successstatus = ChangeWallpaperInstantly("AI Generated")
    }else if(selectedOption == "Anime"){
        successstatus = ChangeWallpaperInstantly("Anime")
    }else if(selectedOption == "Motivation"){
        successstatus = ChangeWallpaperInstantly("Motivation")
    }else if(selectedOption == "Nature"){
        successstatus = ChangeWallpaperInstantly("Nature")
    }else if(selectedOption == "SuperHero"){
        successstatus = ChangeWallpaperInstantly("SuperHero")
    }else if(selectedOption == "Trending"){
        successstatus = ChangeWallpaperInstantly("Trending")
    }

    if(successstatus){
        Toast.makeText(context,"$selectedOption saved as Wallpaper Successfully :)",Toast.LENGTH_LONG).show()
    }
}

@Composable
fun BottomBar(Image:String?) {

    val context = LocalContext.current

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Transparent)){
        Box(modifier = Modifier
            .height(80.dp)
            .width(120.dp)
            .padding(top = 20.dp, start = 40.dp, end = 10.dp)
            .background(colorResource(id = R.color.Light_Blue), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick =
                {
                    val downloader = AndroidDownloader(context2)
                    downloader.downloadFile(Image.toString())
                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.download),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    colorFilter = ColorFilter.tint(Color.White, BlendMode.SrcIn)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .padding(top = 25.dp, end = 10.dp, bottom = 10.dp, start = 20.dp)
                .wrapContentWidth()
                .background(colorResource(id = R.color.Light_Blue), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                val success = SetWallpaper(context, Image.toString())
                if(success){
                    Toast.makeText(context,"Wallpaper changed!!",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Error Changing wallpaper",Toast.LENGTH_LONG).show()
                }
            },colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = "Set as Wallpaper",
                    color = Color.White,
                    fontFamily = Signikafont,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .wrapContentHeight()
                )
            }
        }
    }
}

@Preview
@Composable
fun UI(){
    DetailImage(Image = "https://wallpapers.com/images/high/mobile-4k-gpsxj6sltjbmkkld.webp")
}
