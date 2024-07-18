package com.example.exif_gallery_aos.presentation.photo_grid

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.exif_gallery_aos.domain.photo.ExifModel
import com.example.exif_gallery_aos.domain.photo.PhotoModel
import com.example.exif_gallery_aos.domain.photo.PhotoPresentationModel
import com.example.exif_gallery_aos.presentation.Page
import com.example.exif_gallery_aos.presentation.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PhotoGridPage(navController: NavController, viewModel: PhotoGridPageViewModel = hiltViewModel(), albumName: String, albumId: Int) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val showFilter = remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(state.list.isEmpty()) {
        if (state.list.isEmpty()) {
            viewModel.getPhotoList(albumId)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box{
            PhotoGridPageToolBar(title = albumName, onClickMenu = {
                if (state.isExifLoaded) {
                    scope.launch {
                        showFilter.value = true
                    }
                }
            })
            DropdownMenu(expanded = showFilter.value, onDismissRequest = { showFilter.value = false }) {
                DropdownMenuItem(leadingIcon = {if(state.filter is Filter.DATE){
                    Icon(painter = painterResource(id = R.drawable.baseline_check_24), contentDescription = "")}}, text = { Text(text = "날짜순") }, onClick = { viewModel.changeFilter(Filter.DATE) })
                DropdownMenuItem(leadingIcon = {if(state.filter is Filter.EXIF_MODEL){
                    Icon(painter = painterResource(id = R.drawable.baseline_check_24), contentDescription = "")}}, text = { Text(text = "촬영 기기순") }, onClick = { viewModel.changeFilter(Filter.EXIF_MODEL) })
                DropdownMenuItem(leadingIcon = {if(state.filter is Filter.EXIF_FOCAL_LENGTH){
                    Icon(painter = painterResource(id = R.drawable.baseline_check_24), contentDescription = "")}}, text = { Text(text = "초점 거리순") }, onClick = { viewModel.changeFilter(Filter.EXIF_FOCAL_LENGTH) })
                DropdownMenuItem(leadingIcon = {if(state.filter is Filter.EXIF_F_NUMBER){
                    Icon(painter = painterResource(id = R.drawable.baseline_check_24), contentDescription = "")}}, text = { Text(text = "조리개 순") }, onClick = { viewModel.changeFilter(Filter.EXIF_F_NUMBER) })
            }
        }

        if (state.isLoading) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator(modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally))
            }
        } else {
            if (!state.isExifLoaded) {
                ExifProgressBar(modifier = Modifier.fillMaxWidth(), count = state.exifCount, length = state.list.count())
            }
            PhotoGridBody(list = state.list, filter = state.filter, onClick = { data ->
                navController.navigate(Page.ImageView.route.name + "/${data.photoModel?.photoId?:0}")
            })
        }
    }


}

@Composable
fun PhotoGridPageToolBar(modifier: Modifier = Modifier, title: String, onClickMenu: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onClickMenu) {
            Icon(painter = painterResource(id = R.drawable.baseline_menu_24), contentDescription = "back")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.weight(1f))
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
fun PhotoGridBody(list: List<PhotoPresentationModel>, filter: Filter, onClick: (PhotoPresentationModel) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 5.dp)
    ) {
        items(list.size) { iter ->
            var infoData = ""
            val data = list[iter]

            data.exifModel?.let{
                infoData = when(filter){
                    is Filter.DATE -> it.date
                    is Filter.EXIF_MODEL -> it.model
                    is Filter.EXIF_FOCAL_LENGTH -> it.focalLength
                    is Filter.EXIF_F_NUMBER -> it.fnumber
                }
            }

            PhotoGridItem(path = list[iter].photoModel!!.photoPath, data = infoData, onClick = {
                onClick.invoke(list[iter])
            })
        }
    }
}

@Composable
fun PhotoGridItem(path: String, data: String?, onClick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 10.dp)
        .aspectRatio(1f), onClick = { onClick.invoke() }) {
        Box{
            AsyncImage(modifier = Modifier.fillMaxSize(), model = path, contentDescription = "image_card", contentScale = ContentScale.Crop)
            if (data != null) {
                Text(modifier = Modifier.background(color = Color.Black), text = data, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPhotoGridPage() {
    PhotoGridPageToolBar(title = "asd") {

    }
}

data class PreviewPhoto(override val photoId: Int, override val photoName: String, override val photoPath: String) : PhotoModel
