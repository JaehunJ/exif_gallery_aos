package com.example.exif_gallery_aos.domain.album

import kotlinx.coroutines.flow.Flow

/**
 * 의존성 역전을 위한 인터페이스
 */
interface AlbumRepository {

    fun getAlbumList(): Flow<List<AlbumModel>>
}