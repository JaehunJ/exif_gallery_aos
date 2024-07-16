package com.example.exif_gallery_aos.domain.album

/**
 * 의존성 역전을 위한 인터페이스
 */
interface AlbumModel {
    val albumName: String
    val albumPath: String
    val firstImagePath: String
}
