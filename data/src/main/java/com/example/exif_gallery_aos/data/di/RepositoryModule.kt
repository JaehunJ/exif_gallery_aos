package com.example.exif_gallery_aos.data.di

import android.content.ContentResolver
import android.content.Context
import com.example.exif_gallery_aos.data.datasource.LocalAlbumDataSource
import com.example.exif_gallery_aos.data.datasource.LocalPhotoDataSource
import com.example.exif_gallery_aos.data.repository.AlbumRepositoryImpl
import com.example.exif_gallery_aos.data.repository.PhotoRepositoryImpl
import com.example.exif_gallery_aos.domain.album.AlbumRepository
import com.example.exif_gallery_aos.domain.photo.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContentsResolverModule {

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver = context.contentResolver
}

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideLocalAlbumDataSource(contentResolver: ContentResolver) = LocalAlbumDataSource(contentResolver)

    @Provides
    @Singleton
    fun provideLocalPhotoDataSource(contentResolver: ContentResolver) = LocalPhotoDataSource(contentResolver)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAlbumRepository(albumRepositoryImpl: AlbumRepositoryImpl): AlbumRepository

    @Binds
    abstract fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository
}


