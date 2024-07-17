package com.example.exif_gallery_aos.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.exif_gallery_aos.presentation.album_grid.AlbumGridPage
import com.example.exif_gallery_aos.presentation.image.ImageViewPage
import com.example.exif_gallery_aos.presentation.photo_grid.PhotoGridPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExifGalleryAppScreen() {
    var navController = rememberNavController();

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = Page.AlbumGrid.route.name) {
            composable(Page.AlbumGrid.route.name) {
                AlbumGridPage(navController = navController)
            }
            composable(Page.PhotoGrid.route.name + "/{albumName}/{albumId}", arguments = listOf(
                navArgument("albumName") { type = NavType.StringType },
                navArgument("albumId") { type = NavType.IntType },
            )) {
                PhotoGridPage(
                    navController = navController,
                    albumName = it.arguments?.getString("albumName") ?: "",
                    albumId = it.arguments?.getInt("albumId") ?: 0
                )
            }
            composable(Page.ImageView.route.name + "/{photoPath}", arguments = listOf()) {
                ImageViewPage(navController = navController)
            }
        }
    }
}