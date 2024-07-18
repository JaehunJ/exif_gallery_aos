package com.example.exif_gallery_aos.data.entity

import com.example.exif_gallery_aos.domain.photo.ExifModel

class ExifEntity(override val model: String, override val focalLength: String, override val fnumber: String, override val date: String) : ExifModel {
    override fun toString(): String {
        return "${model} / ${focalLength} / ${fnumber} / ${date}"
    }
}