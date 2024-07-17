package com.example.exif_gallery_aos.presentation.photo_grid

import android.util.Log
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exif_gallery_aos.domain.photo.ExifModel
import com.example.exif_gallery_aos.domain.photo.GetPhotoListUseCase
import com.example.exif_gallery_aos.domain.photo.PhotoPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                val list = it.map {
                    PhotoPresentationModel(it, null)
                }
                withContext(Dispatchers.Main) {
                    _state.emit(_state.value.copy(list = list, isLoading = false))

                    // exif load
                    loadExifDataFromAllPhoto()
                }
            }
        }
    }

    suspend fun loadExifDataFromAllPhoto() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = _state.value.list

            list.forEachIndexed {iter, item->
                val exif = ExifInterface(item.photoModel!!.photoPath)

                var focalLength = exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH)?:""

                if(focalLength.contains("/")){
                    val split = focalLength.split("/")
                    if(split.isNotEmpty()){
                        focalLength = (split[0].toFloat()/split[1].toFloat()).toString()
                    }
                }

                val exifInfo = ExifModel(
                    model = "${exif.getAttribute(ExifInterface.TAG_MAKE)} ${exif.getAttribute(ExifInterface.TAG_MODEL)}",
                    focalLength = focalLength,
                    fnumber = exif.getAttribute(ExifInterface.TAG_F_NUMBER)?:"",
                    date = exif.getAttribute(ExifInterface.TAG_DATETIME)?:"",
                )

                item.exifModel = exifInfo

                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(exifCount = (iter+1), isExifLoaded = iter == list.count() - 1)
                    }
                }
            }
        }
    }

}

data class PhotoGridPageState(val list: List<PhotoPresentationModel>, val filter: Filter, val isLoading: Boolean, val exifCount: Int, val isExifLoaded: Boolean)

sealed class Filter {
    object DATE : Filter()
    object EXIF_MODEL : Filter()
    object EXIF_FOCAL_LENGTH : Filter()
    object EXIF_F_NUMBER : Filter()
}