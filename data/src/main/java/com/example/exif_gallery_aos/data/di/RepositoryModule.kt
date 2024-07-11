package com.example.exif_gallery_aos.data.di

import com.example.exif_gallery_aos.data.repository.AlbumRepositoryImpl
import com.example.exif_gallery_aos.data.repository.PhotoRepositoryImpl
import com.example.exif_gallery_aos.domain.album.AlbumRepository
import com.example.exif_gallery_aos.domain.photo.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindAlbumRepository(albumRepositoryImpl: AlbumRepositoryImpl): AlbumRepository

    @Binds
    @Singleton
    fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository
}
