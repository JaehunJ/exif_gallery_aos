package com.example.exif_gallery_aos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.exif_gallery_aos.presentation.ExifGalleryAppScreen
import com.example.exif_gallery_aos.ui.theme.Exif_gallery_aosTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_MEDIA_IMAGES,
    )

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Exif_gallery_aosTheme {
                var showApp by remember {
                    mutableStateOf(true)
                }

                RequestPermissionCom(permissions = permissions, onPermissionResult = {
                    if(it.all { item->item.value }){
                        showApp = true
                    }
                })

                if(showApp){
                    ExifGalleryAppScreen()
                }
            }
        }
    }
}

@Composable
fun RequestPermissionCom(
    permissions: Array<String>,
    onPermissionResult: (Map<String, Boolean>) -> Unit
) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        onPermissionResult(permissionsMap)
    }
    val scope = rememberCoroutineScope()

    val permissionsState = remember {
        mutableStateMapOf<String, Boolean>().apply {
            permissions.forEach { permission ->
                this[permission] = ContextCompat.checkSelfPermission(
                    context, permission
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    LaunchedEffect(permissionsState) {
        if (permissionsState.all { it.value }) {
            onPermissionResult(permissionsState)
        } else {
            scope.launch { permissionLauncher.launch(permissions) }
        }
    }
}