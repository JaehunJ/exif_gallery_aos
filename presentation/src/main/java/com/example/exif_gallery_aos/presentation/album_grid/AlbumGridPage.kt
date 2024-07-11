package com.example.exif_gallery_aos.presentation.album_grid

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumGridPage(navController: NavController = rememberNavController()){
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Text(text = "asd")
    }
}

@Preview
@Composable
fun PreviewAlbumGridPage(){
    AlbumGridPage();
}