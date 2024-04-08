package com.example.wallpaper.Images

import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.wallpaper.AIGenerated.AIGeneratedItem
import com.example.wallpaper.AIGenerated.AIGeneratedScreen
import com.example.wallpaper.AIGenerated.getAIgenerated
import com.example.wallpaper.Anime.AnimeItem
import com.example.wallpaper.Anime.AnimeScreen
import com.example.wallpaper.Anime.getAnime
import com.example.wallpaper.MainActivity2
import com.example.wallpaper.Motivation.MotivationItem
import com.example.wallpaper.Motivation.MotivationScreen
import com.example.wallpaper.Motivation.getMotivation
import com.example.wallpaper.Nature.NatureItem
import com.example.wallpaper.Nature.NatureScreen
import com.example.wallpaper.Nature.getNature
import com.example.wallpaper.PostData.dropDownItem
import com.example.wallpaper.R
import com.example.wallpaper.SetWallpaper
import com.example.wallpaper.SuperHero.SuperHeroItem
import com.example.wallpaper.SuperHero.SuperHeroScreen
import com.example.wallpaper.SuperHero.getsuperhero
import com.example.wallpaper.Trending.TrendingScreen
import com.example.wallpaper.Trending.getTrends
import com.example.wallpaper.Trending.trendingItem
import com.example.wallpaper.test
import com.example.wallpaper.ui.theme.Acme
import com.example.wallpaper.ui.theme.Amaranth
import com.example.wallpaper.ui.theme.Chivo
import com.example.wallpaper.ui.theme.playfair
import kotlinx.coroutines.launch
import java.time.LocalTime
import kotlin.random.Random

lateinit var currentTime : LocalTime
var currentHour : Int = 0

lateinit var Imagelist : List<MiscellaneousItem>
lateinit var trends :List<trendingItem>
lateinit var aigenerated : List<AIGeneratedItem>
lateinit var anime: List<AnimeItem>
lateinit var motivation : List<MotivationItem>
lateinit var nature :List<NatureItem>
lateinit var superhero :List<SuperHeroItem>

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
fun ImageModelScreen(){

    loadallgenre()

    trends  = getTrends()
    aigenerated = getAIgenerated()
    anime = getAnime()
    motivation = getMotivation()
    nature = getNature()
    superhero = getsuperhero()

    val Images : MainviewModel = viewModel()
    val viewstate by Images.ImageState

    Box(modifier = Modifier.fillMaxSize()){
        when{
            viewstate.loading ->{
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            viewstate.error != null->{
                Text(text = "ERROR OCCURRED \n ${viewstate.error}")
                Log.e("View State.error -> " , viewstate.error!!)
            }else ->{
                Imagelist = viewstate.list
                ImageScreen(viewstate.list)
            }
        }
    }
}

@Composable
fun loadallgenre(){
    TrendingScreen()
    AIGeneratedScreen()
    MotivationScreen()
    NatureScreen()
    AnimeScreen()
    SuperHeroScreen()
}

var showappbar = true

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(Images : List<MiscellaneousItem>){

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
        )
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedItemIndex by rememberSaveable {mutableStateOf(0)}

    var showdropdown by remember { mutableStateOf(false) }

    var selectedOption by remember{ mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        if(showappbar){
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(modifier = Modifier.height(20.dp))
                        items.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text = item.title,
                                        color = Black,
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
                                        Toast.makeText(LocalContext.current,"Select Your Favourite Genre to set wallpaper",Toast.LENGTH_LONG).show()
                                        DropdownMenu(
                                            expanded = showdropdown,
                                            onDismissRequest = { showdropdown=false }) {
                                            dropdownitem.forEachIndexed { index, item ->
                                                androidx.compose.material.DropdownMenuItem(
                                                    onClick = {
                                                        showdropdown=false
                                                        selectedOption = item.title
                                                    }
                                                ) {
                                                    androidx.compose.material.Text(
                                                        item.title,
                                                        color = Black,
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
                    }

                ) {paddingValue ->
                    Column(modifier = Modifier.padding(paddingValue)){
                        Topbar()

                        LazyVerticalGrid(
                            GridCells.Fixed(3), modifier = Modifier.fillMaxSize()
                        ){
                            items(Images){Image ->
                                ImageItem(Image)
                            }
                        }
                    }
                }
            }

        }

        Log.d("Selected option" , selectedOption)

        LazyVerticalGrid(
            GridCells.Fixed(3), modifier = Modifier.fillMaxSize()
        ){
            items(Images){Image ->
                ImageItem(Image)
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
fun ImageItem(Images: MiscellaneousItem){

    var showDetail by remember{mutableStateOf(false)}

    Button(onClick = {
        showDetail = true
    },colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
        Image(
            painter = rememberAsyncImagePainter(model = Images.imageUrl),
            contentDescription = "",
            modifier = Modifier.size(280.dp,250.dp).
            aspectRatio(9f/16f)
        )
    }

    val context = LocalContext.current

    if(showDetail){
        val intent = Intent(context, test::class.java)
        intent.putExtra("ImageUrl",Images.imageUrl)
        context.startActivity(intent)
    }
    showDetail=false
}

@Composable
fun Topbar() {
    val scrollState = rememberScrollState()

    var showImage by remember{mutableStateOf(false)}
    var showTrending by remember{mutableStateOf(false)}
    var showAiGenerated by remember{mutableStateOf(false)}
    var showSuperHero by remember{mutableStateOf(false)}
    var showAnime by remember{mutableStateOf(false)}
    var showNature by remember{mutableStateOf(false)}
    var showmotivation by remember{mutableStateOf(false)}

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(top = 20.dp)
            .background(Color.Transparent)
    ) {
        Box(contentAlignment = Alignment.Center){
            Button(onClick = {
                showImage=true
                showTrending=false
                showAiGenerated=false
                showSuperHero=false
                showAnime=false
                showNature=false
                showmotivation=false
                showappbar=false
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                CustomText(text = "Miscellaneous")
            }
        }
        Box(contentAlignment = Alignment.Center){
            Button(onClick = {
                showImage=false
                showTrending=true
                showAiGenerated=false
                showSuperHero=false
                showAnime=false
                showNature=false
                showmotivation=false
                showappbar=false
            },colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                CustomText(text = "Trending")
            }
        }
        Button(onClick = {
            showImage=false
            showTrending=false
            showAiGenerated=true
            showSuperHero=false
            showAnime=false
            showNature=false
            showmotivation=false
            showappbar=false
        },colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
            CustomText(text = "AI Generated")
        }
        Button(onClick = {
            showImage=false
            showTrending=false
            showAiGenerated=false
            showSuperHero=true
            showAnime=false
            showNature=false
            showmotivation=false
            showappbar=false
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            CustomText(text = "SuperHero")
        }
        Button(onClick = {
            showImage=false
            showTrending=false
            showAiGenerated=false
            showSuperHero=false
            showAnime=true
            showNature=false
            showmotivation=false
            showappbar=false
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            CustomText(text = "Anime")
        }
        Button(onClick = {
            showImage=false
            showTrending=false
            showAiGenerated=false
            showSuperHero=false
            showAnime=false
            showNature=true
            showmotivation=false
            showappbar=false
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            CustomText(text = "Nature")
        }
        Button(onClick = {
            showImage=false
            showTrending=false
            showAiGenerated=false
            showSuperHero=false
            showAnime=false
            showNature=false
            showmotivation=true
            showappbar=false
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            CustomText("Motivation")
        }
    }

    var loading by remember {mutableStateOf(false)}

    if(showImage){
        loading=true
        ImageModelScreen()
        loading=false
    }
    if(showTrending){
        loading=true
        TrendingScreen()
        loading=false
    }
    if(showAnime){
        loading=true
        AnimeScreen()
        loading=false
    }
    if(showAiGenerated){
        loading=true
        AIGeneratedScreen()
        loading=false
    }
    if(showNature){
        loading=true
        NatureScreen()
        loading=false
    }
    if(showSuperHero){
        loading=true
        SuperHeroScreen()
        loading=false
    }
    if(showmotivation){
        loading=true
        MotivationScreen()
        loading=false
    }

    if(loading){
        Pb(true)
    }
}

@Composable
fun CustomText(text:String){
    Box(modifier =
    Modifier
        .height(35.dp)
        .width(130.dp)
        .background(colorResource(id = R.color.Light_Blue), RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ){
        Text(text = text,
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = Amaranth
        )
    }
}

@Composable
fun Pb(currstate:Boolean) {

    Column(modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ){
        var show by remember { mutableStateOf(currstate) }

        if(show){
            CircularProgressIndicator(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp),
                color = Black,
                strokeWidth = 5.dp
            )
        }
    }
}

@Composable
fun ChangeWallpaperInstantly(type:String) : Boolean{

    val context = LocalContext.current
    var url:String = ""
    if(type == "AI Generated"){
        val randomIndex = Random.nextInt(aigenerated.size)
        url  = aigenerated[randomIndex].imageUrl
    }else if(type == "Anime"){
        val randomIndex = Random.nextInt(anime.size)
        url  = anime[randomIndex].imageUrl
    }else if(type == "Miscellaneous"){
        val randomIndex = Random.nextInt(Imagelist.size)
        url  = Imagelist[randomIndex].imageUrl
    }else if(type == "Motivation"){
        val randomIndex = Random.nextInt(motivation.size)
        url  = motivation[randomIndex].imageUrl
    }else if(type == "Nature"){
        val randomIndex = Random.nextInt(nature.size)
        url  = nature[randomIndex].imageUrl
    }else if(type == "SuperHero"){
        val randomIndex = Random.nextInt(superhero.size)
        url  = superhero[randomIndex].imageUrl
    }else if(type == "Trending"){
        val randomIndex = Random.nextInt(trends.size)
        url  = trends[randomIndex].imageUrl
    }

    SetWallpaper(context,url)

    return true
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun help(){
    currentTime = LocalTime.NOON
    currentHour = currentTime.hour

}
