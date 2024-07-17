package com.example.exif_gallery_aos.presentation.photo_grid

import androidx.lifecycle.ViewModel
import com.example.exif_gallery_aos.domain.photo.GetPhotoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoGridPageViewModel @Inject constructor(private val getPhotoListUseCase: GetPhotoListUseCase) : ViewModel() {
}