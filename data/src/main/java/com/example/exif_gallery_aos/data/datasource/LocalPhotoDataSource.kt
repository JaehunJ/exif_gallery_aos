package com.example.exif_gallery_aos.data.datasource

import android.content.ContentResolver
import android.media.ExifInterface
import android.provider.MediaStore
import com.example.exif_gallery_aos.data.entity.ExifEntity
import com.example.exif_gallery_aos.data.entity.ImageEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalPhotoDataSource @Inject constructor(private val contentResolver: ContentResolver) {
    fun getPhotoList(albumId: Int) = flow<List<ImageEntity>> {
        val list = mutableListOf<ImageEntity>()

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA),
            MediaStore.Images.Media.BUCKET_ID + "= ?",
            arrayOf(albumId.toString()),
            null,
        )

        cursor?.let {
            while (cursor.moveToNext()) {
                val photoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val photoName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val photoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                list.add(ImageEntity(photoId, photoName, photoPath))
            }
            cursor.close()
        }

        emit(list)
    }

    fun getPhoto(photoId: Int) = flow<ImageEntity> {
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA),
            MediaStore.Images.Media._ID + "= ?",
            arrayOf(photoId.toString()),
            null,
        )

        cursor?.let {
            if (cursor.moveToFirst()) {
                val photoName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val photoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                emit(ImageEntity(photoId, photoName, photoPath))
            }
            cursor.close()
        }
    }

    fun getExif(path: String) = flow<ExifEntity> {
        val exif = ExifInterface(path)

        var focalLength = exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH) ?: ""

        if (focalLength.contains("/")) {
            val split = focalLength.split("/")
            if (split.isNotEmpty()) {
                focalLength = (split[0].toFloat() / split[1].toFloat()).toString()
            }
        }

        val exifInfo = ExifEntity(
            model = "${exif.getAttribute(ExifInterface.TAG_MAKE)} ${exif.getAttribute(ExifInterface.TAG_MODEL)}",
            focalLength = focalLength,
            fnumber = exif.getAttribute(ExifInterface.TAG_F_NUMBER) ?: "",
            date = exif.getAttribute(ExifInterface.TAG_DATETIME) ?: "",
        )

        emit(exifInfo)
    }
}