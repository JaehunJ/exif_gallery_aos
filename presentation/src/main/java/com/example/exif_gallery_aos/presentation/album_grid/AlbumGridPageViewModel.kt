package com.example.exif_gallery_aos.presentation.album_grid

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exif_gallery_aos.domain.album.AlbumModel
import com.example.exif_gallery_aos.domain.album.GetAlbumListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 */
@HiltViewModel
class AlbumGridPageViewModel @Inject constructor(private val getAlbumListUseCase: GetAlbumListUseCase) : ViewModel() {
    private val _state = MutableStateFlow<AlbumGridPageState>(AlbumGridPageState(list = emptyList(), isLoading = true))
    val state = _state.asStateFlow()

    fun getAlbumList() {
        viewModelScope.launch(Dispatchers.IO) {
            getAlbumListUseCase.invoke(null).collect {list->
                _state.update { prev->
                    prev.copy(list = list, isLoading = false)
                }
            }
        }
    }
}

/**
 * view state
 */
data class AlbumGridPageState(var list: List<AlbumModel>, val isLoading: Boolean)