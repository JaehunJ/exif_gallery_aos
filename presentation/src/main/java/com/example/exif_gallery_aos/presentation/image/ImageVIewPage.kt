package com.example.exif_gallery_aos.presentation.image

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun ImageViewPage(navController: NavController, viewModel: ImageViewPageViewModel = hiltViewModel(), photoId:Int) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.value.photoModel == null) {
        viewModel.getPhoto(photoId)
    }

    Surface {
        if (state.value.photoModel != null) {
            AsyncImage(model = state.value.photoModel!!.photoPath, contentDescription = "")
        } else {
            Text(text = "Loading...")
        }
    }
}