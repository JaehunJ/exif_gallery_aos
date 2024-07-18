package com.example.exif_gallery_aos.domain.photo


interface ExifModel {
    val model: String
    val focalLength: String
    val fnumber: String
    val date: String
}