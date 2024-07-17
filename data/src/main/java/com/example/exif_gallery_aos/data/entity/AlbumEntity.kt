package com.example.exif_gallery_aos.data.entity

import com.example.exif_gallery_aos.domain.album.AlbumModel

/**
 * album model impl, 의존성 역전
 */
data class AlbumEntity(override val albumId:Int, override val albumName: String, override val albumPath: String, override val firstImagePath: String) :
    AlbumModel
