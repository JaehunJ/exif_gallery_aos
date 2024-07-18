package com.example.exif_gallery_aos.presentation.album_grid

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.exif_gallery_aos.domain.album.AlbumModel
import com.example.exif_gallery_aos.presentation.Page
import com.example.exif_gallery_aos.presentation.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumGridPage(navController: NavController = rememberNavController(), viewModel: AlbumGridPageViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    /**
     * 처음 정보 불러오기
     */
    LaunchedEffect(state.list.isEmpty()) {
        if (state.list.isEmpty()) {
            viewModel.getAlbumList()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        AlbumGridPageToolBar(title = "Exif Gallery")
        if (state.isLoading) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        } else {
            AlbumGridBody(list = state.list, onClickCard = { model ->
                navController.navigate(Page.PhotoGrid.route.name + "/${model.albumName}/${model.albumId}")
            })
        }

    }
}

@Composable
fun AlbumGridPageToolBar(modifier :Modifier = Modifier, title:String){
    Row(verticalAlignment = Alignment.CenterVertically){
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun AlbumGridBody(modifier: Modifier = Modifier.fillMaxSize(), list: List<AlbumModel>, onClickCard: (AlbumModel) -> Unit) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(3), contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp)) {
        items(list.size) { iter ->
            AlbumGridItem(list[iter], onClickCard)
        }
    }
}

@Composable
fun AlbumGridItem(data: AlbumModel, onClickCard: (AlbumModel) -> Unit) {
    Column(modifier = Modifier.clickable {
        onClickCard.invoke(data)
    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp)
                .aspectRatio(1f)
        ) {
            AsyncImage(modifier = Modifier.fillMaxSize(), model = data.firstImagePath, contentDescription = "", contentScale = ContentScale.Crop)
        }
        Text(modifier = Modifier.fillMaxWidth(), text = data.albumName, style = TextStyle(textAlign = TextAlign.Center))
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAlbumGridPage() {
    AlbumGridBody(list = listOf(Dummy(albumId = 0,"test", "test", "test")), onClickCard = {

    })
}

class Dummy(override val albumId: Int, override val albumName: String, override val albumPath: String, override val firstImagePath: String) :AlbumModel