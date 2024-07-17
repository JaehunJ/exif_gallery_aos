package com.example.exif_gallery_aos.domain.photo

import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getPhotoList(albumId: Int): Flow<List<PhotoModel>>
    fun getPhoto(photoId: Int): Flow<PhotoModel>
}