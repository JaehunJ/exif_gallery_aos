package com.example.exif_gallery_aos.presentation.photo_grid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exif_gallery_aos.domain.photo.GetPhotoListUseCase
import com.example.exif_gallery_aos.domain.photo.PhotoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PhotoGridPageViewModel @Inject constructor(private val getPhotoListUseCase: GetPhotoListUseCase) : ViewModel() {
    private val _state =
        MutableStateFlow<PhotoGridPageState>(
            PhotoGridPageState(
                list = emptyList(),
                filter = Filter.DATE,
                isLoading = true,
                exifCount = 0,
                isExifLoaded = false
            )
        )
    val state = _state.asStateFlow()

    fun getPhotoList(albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPhotoListUseCase.invoke(GetPhotoListUseCase.Params(albumId)).collect {
                withContext(Dispatchers.Main) {
                    _state.emit(_state.value.copy(list = it, isLoading = false))
                }
            }
        }
    }

    fun loadExifDataFromAllPhoto() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}

data class PhotoGridPageState(val list: List<PhotoModel>, val filter: Filter, val isLoading: Boolean, val exifCount: Int, val isExifLoaded: Boolean)

sealed class Filter {
    object DATE : Filter()
    object EXIF_MODEL : Filter()
    object EXIF_FOCAL_LENGTH : Filter()
    object EXIF_F_NUMBER : Filter()
}