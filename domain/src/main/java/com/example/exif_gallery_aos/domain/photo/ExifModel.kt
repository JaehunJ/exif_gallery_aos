package com.example.exif_gallery_aos.domain.photo


data class ExifModel(
    private val model:String,
    private val focalLength:String,
    private val fnumber:String,
    private val date:String
){
    override fun toString(): String {
        return "${model} / ${focalLength} / ${fnumber} / ${date}"
    }
}