package com.example.exif_gallery_aos.presentation.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.exif_gallery_aos.domain.photo.ExifModel
import com.example.exif_gallery_aos.domain.photo.PhotoModel
import com.example.exif_gallery_aos.presentation.R

@Composable
fun ImageViewPage(navController: NavController, viewModel: ImageViewPageViewModel = hiltViewModel(), photoId: Int) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.value.photoModel == null) {
        viewModel.getPhoto(photoId)
    }

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        if (state.value.photoModel != null) {
            AsyncImage(model = state.value.photoModel!!.photoPath, contentDescription = "")
        } else {
            Text(text = "Loading...")
        }
    }
}

@Composable
fun ImageViewBody(state: ImageViewState, isPreview: Boolean = LocalInspectionMode.current) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (isPreview) {
            PinchZoomImage(
                modifier = Modifier.fillMaxSize(),
                model = painterResource(id = R.drawable.baseline_menu_24),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
        } else {
            PinchZoomImage(
                modifier = Modifier.fillMaxSize(),
                model = state.photoModel!!.photoPath,
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
        }
        Row(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(20.dp)) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.baseline_share_24), contentDescription = "", tint = Color.White)
            }
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0x22000000))){
            Text(text = "Exif Info", style = MaterialTheme.typography.titleLarge.copy(color = Color.White))
            Text(text = "model ${state.exifModel?.model?:""}", style = TextStyle(color = Color.White))
            Text(text = "f-number ${state.exifModel?.fnumber?:""}", style = TextStyle(color = Color.White))
            Text(text = "focal length ${state.exifModel?.focalLength?:""}", style = TextStyle(color = Color.White))
        }

        Text(text = "asdasd", modifier = Modifier.align(Alignment.Center), style = TextStyle(color = Color.White))
    }
}

@Composable
fun PinchZoomImage(
    model: Any?, modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentDescription: String = "",
) {
    val screenWidth = with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val screenHeight = with(LocalDensity.current) { LocalConfiguration.current.screenHeightDp.dp.toPx() }

    val scale = remember {
        mutableStateOf(1f)
    }
    val offset = remember {
        mutableStateOf(Offset.Zero)
    }

    val pinchModifier = modifier
        .pointerInput(Unit) {
            detectTransformGestures { centroid, pan, zoom, _ ->
                scale.value *= zoom

                offset.value += Offset(
                    x = (1 - zoom) * (centroid.x - offset.value.x - screenWidth),
                    y = (1 - zoom) * (centroid.y - offset.value.y - screenHeight)
                ) + pan
            }
        }
        .graphicsLayer(scaleX = scale.value, scaleY = scale.value, translationX = offset.value.x, translationY = offset.value.y)

    Box(modifier = pinchModifier) {
        if (model is Painter) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = model,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                contentDescription = ""
            )
        } else {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = model,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun PreviewImageViewPage() {
    ImageViewBody(
        state = ImageViewState(
            photoModel = PreviewPhoto(photoId = 1, photoName = "test", photoPath = ""),
            exifModel = PreviewExif(date = "test", model = "test", focalLength = "test", fnumber = "test")
        )
    )
}

data class PreviewPhoto(override val photoId: Int, override val photoName: String, override val photoPath: String) : PhotoModel
data class PreviewExif(override val date: String, override val model: String, override val focalLength: String, override val fnumber: String) :
    ExifModel