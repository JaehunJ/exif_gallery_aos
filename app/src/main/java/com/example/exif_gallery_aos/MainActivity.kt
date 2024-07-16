package com.example.exif_gallery_aos

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.exif_gallery_aos.presentation.ExifGalleryAppScreen
import com.example.exif_gallery_aos.ui.theme.Exif_gallery_aosTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,

    )

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Exif_gallery_aosTheme {
                val permissionState = rememberMultiplePermissionsState(permissions = permissions.toList())
                var showApp by remember {
                    mutableStateOf(true)
                }

                LaunchedEffect(permissionState) {
                    if (!permissionState.allPermissionsGranted) {
                        permissionState.launchMultiplePermissionRequest()
                    } else {
                        showApp = true
                    }
                }

                if(showApp){
                    ExifGalleryAppScreen()
                }
            }
        }
    }
}