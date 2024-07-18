package com.example.exif_gallery_aos.presentation.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exif_gallery_aos.domain.photo.ExifModel
import com.example.exif_gallery_aos.domain.photo.GetPhotoExifUseCase
import com.example.exif_gallery_aos.domain.photo.GetPhotoUseCase
import com.example.exif_gallery_aos.domain.photo.PhotoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewPageViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val getPhotoExifUseCase: GetPhotoExifUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ImageViewState>(ImageViewState(null, null))
    val state = _state.asStateFlow()

    fun getPhoto(photoId: Int) {
        viewModelScope.launch {
            getPhotoUseCase.invoke(GetPhotoUseCase.Params(photoId)).collect { model ->
                getPhotoExifUseCase.invoke(params = GetPhotoExifUseCase.Params(model.photoPath)).collect { exif ->
                    _state.update {
                        it.copy(photoModel = model, exifModel = exif)
                    }
                }
            }
        }
    }
}

data class ImageViewState(val photoModel: PhotoModel?, val exifModel: ExifModel?)