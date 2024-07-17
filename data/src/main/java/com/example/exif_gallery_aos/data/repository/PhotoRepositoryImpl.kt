package com.example.exif_gallery_aos.data.repository

import com.example.exif_gallery_aos.data.datasource.LocalPhotoDataSource
import com.example.exif_gallery_aos.domain.photo.PhotoModel
import com.example.exif_gallery_aos.domain.photo.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val localPhotoDataSource: LocalPhotoDataSource) : PhotoRepository {
    override fun getPhotoList(albumId: Int): Flow<List<PhotoModel>> {
        return localPhotoDataSource.getPhotoList(albumId)
    }

    override fun getPhoto(photoId: Int): Flow<PhotoModel> {
        TODO("Not yet implemented")
    }
}