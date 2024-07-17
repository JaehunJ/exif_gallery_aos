package com.example.exif_gallery_aos.data.entity

import com.example.exif_gallery_aos.domain.photo.PhotoModel

data class ImageEntity(override val photoId: Int, override val photoName: String, override val photoPath: String) : PhotoModel
