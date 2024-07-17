package com.example.exif_gallery_aos.domain.photo

data class PhotoPresentationModel(
    val photoModel: PhotoModel? = null,
    var exifModel: ExifModel? = null
) {
    companion object {
        fun build(photoModel: PhotoModel, exifModel: ExifModel?): PhotoPresentationModel {
            return PhotoPresentationModel(photoModel, exifModel)
        }
    }
}