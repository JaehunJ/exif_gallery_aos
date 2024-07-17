package com.example.exif_gallery_aos.presentation.photo_grid

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.exif_gallery_aos.domain.photo.PhotoModel
import com.example.exif_gallery_aos.domain.photo.PhotoPresentationModel
import com.example.exif_gallery_aos.presentation.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PhotoGridPage(navController: NavController, viewModel: PhotoGridPageViewModel = hiltViewModel(), albumName: String, albumId: Int) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.list.isEmpty()) {
        if (state.list.isEmpty()) {
            viewModel.getPhotoList(albumId)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = albumName) }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_menu_24), contentDescription = "")
                }
            })
        }
    ) {
        if (state.isLoading) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally))
            }
        } else {
            if (!state.isExifLoaded) {
                ExifProgressBar(modifier = Modifier.fillMaxWidth(), count = state.exifCount, length = state.list.count())
            }
            PhotoGridBody(list = state.list, onClick = { data ->
                Log.e("#debug", "${data.exifModel}")
            })
        }
    }
}

@Composable
fun ExifProgressBar(modifier: Modifier, count: Int = 0, length: Int = 10) {
    Row(modifier = modifier.padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$count / $length")
        Spacer(modifier = Modifier.width(10.dp))
        LinearProgressIndicator(
            progress = { count.toFloat() / length.toFloat() },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun PhotoGridBody(list: List<PhotoPresentationModel>, onClick: (PhotoPresentationModel) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp)
    ) {
        items(list.size) { iter ->
            PhotoGridItem(path = list[iter].photoModel!!.photoPath, onClick = {
                onClick.invoke(list[iter])
            })
        }
    }
}

@Composable
fun PhotoGridItem(path: String, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 10.dp)
        .aspectRatio(1f), onClick = { onClick.invoke() }) {
        AsyncImage(modifier = Modifier.fillMaxSize(), model = path, contentDescription = "image_card", contentScale = ContentScale.Crop)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhotoGridPage() {
    Column {
        ExifProgressBar(modifier = Modifier.fillMaxWidth(), count = 10)
    }


//    val list = mutableListOf<PreviewPhoto>()
//    repeat(30) {
//        list.add(PreviewPhoto(0, "", ""))
//    }
//
//    PhotoGridBody(
//        list = list, onClick = {})
}

data class PreviewPhoto(override val photoId: Int, override val photoName: String, override val photoPath: String) : PhotoModel
