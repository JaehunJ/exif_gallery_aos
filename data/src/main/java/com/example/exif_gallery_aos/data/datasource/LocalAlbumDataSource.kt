package com.example.exif_gallery_aos.data.datasource

import android.content.ContentResolver
import android.provider.MediaStore
import android.util.Log
import com.example.exif_gallery_aos.data.entity.AlbumEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * local albumdatasource
 */
class LocalAlbumDataSource @Inject constructor(private val contentResolver: ContentResolver) {
    fun getAlbumList() = flow<List<AlbumEntity>> {
        val list = mutableListOf<AlbumEntity>()
        val idList = mutableListOf<Int>()
        val uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val cursor = contentResolver.query(
            uriExternal,
            arrayOf(MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA),
            null,
            null,
            MediaStore.Images.Media.DATE_TAKEN
        )

        cursor?.let { cursorFolder->
            while (cursorFolder.moveToNext()) {
                val albumId = cursorFolder.getInt(cursorFolder.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID))
                val albumName = cursorFolder.getString(cursorFolder.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                val albumPath = cursorFolder.getString(cursorFolder.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                if(!idList.contains(albumId) && !albumName.lowercase().contains("download")){
                    val cursorThumbnail = contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        arrayOf(MediaStore.Images.Media.DATA),
                        MediaStore.Images.Media.BUCKET_ID + "= ?",
                        arrayOf(albumId.toString()),
                        null,
                    )

                    cursorThumbnail?.let {
                        if (cursorThumbnail.moveToFirst()) {
                            val thumbnailUri = cursorThumbnail.getString(cursorThumbnail.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                            list.add(AlbumEntity(albumId = albumId, albumName = albumName, albumPath = albumPath, firstImagePath = thumbnailUri))
                            idList.add(albumId)
                        }

                        cursorThumbnail.close()
                    }
                }

            }
            cursorFolder.close()
        }

        emit(list)
    }
}