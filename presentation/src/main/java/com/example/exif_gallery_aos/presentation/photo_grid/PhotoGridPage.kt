package com.example.exif_gallery_aos.presentation.photo_grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.exif_gallery_aos.domain.photo.PhotoModel


@Composable
fun PhotoGridPage(navController: NavController, viewModel: PhotoGridPageViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .statusBarsPadding()
    ) {

    }
}

@Composable
fun PhotoGridBody(list: List<PhotoModel>, onClick: (PhotoModel) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp)
    ) {
        items(20) { iter ->
            PhotoGridItem(data = list[iter], onClick)
        }
    }
}

@Composable
fun PhotoGridItem(data: PhotoModel, onClick: (PhotoModel) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 10.dp)
        .aspectRatio(1f), onClick = { onClick.invoke(data) }) {
        AsyncImage(model = "", contentDescription = "")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhotoGridPage() {

    val list = mutableListOf<PreviewPhoto>()
    repeat(30){
        list.add(PreviewPhoto(0,"", ""))
    }

    PhotoGridBody(
        list = list, onClick = {})
}

data class PreviewPhoto(override val photoId: Int, override val photoName: String, override val photoPath: String) : PhotoModel
