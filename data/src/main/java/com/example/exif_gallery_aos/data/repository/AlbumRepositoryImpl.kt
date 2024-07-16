package com.example.exif_gallery_aos.data.repository

import com.example.exif_gallery_aos.data.datasource.LocalAlbumDataSource
import com.example.exif_gallery_aos.domain.album.AlbumModel
import com.example.exif_gallery_aos.domain.album.AlbumRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 앨범 리포지토리 구현
 */
class AlbumRepositoryImpl @Inject constructor(private val localAlbumDataSource:LocalAlbumDataSource) : AlbumRepository {

    /**
     * flow 리턴
     */
    override fun getAlbumList(): Flow<List<AlbumModel>> {
        return localAlbumDataSource.getAlbumList()
    }
}