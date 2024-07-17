package com.example.exif_gallery_aos.data.datasource

import android.content.ContentResolver
import android.provider.MediaStore
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

    fun getPhoto(photoId:Int) = flow<ImageEntity> {
//        val
        //todo:not implemented
    }
}