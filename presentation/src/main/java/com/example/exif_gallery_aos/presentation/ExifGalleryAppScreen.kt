package com.example.exif_gallery_aos.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exif_gallery_aos.presentation.album_grid.AlbumGridPage
import com.example.exif_gallery_aos.presentation.image.ImageViewPage
import com.example.exif_gallery_aos.presentation.photo_grid.PhotoGridPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExifGalleryAppScreen() {
    var navController = rememberNavController();

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        NavHost(navController = navController, startDestination = Page.AlbumGrid.route.name) {
            composable(Page.AlbumGrid.route.name) {
                AlbumGridPage(navController = navController)
            }
            composable(Page.PhotoGrid.route.name) {
                PhotoGridPage(navController = navController)
            }
            composable(Page.ImageView.route.name) {
                ImageViewPage(navController = navController)
            }
        }
    }
}