package com.example.exif_gallery_aos.domain.album

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlbumUseCaseProvider{

    @Provides
    @Singleton
    fun provideGetAlbumListUseCase(albumRepository: AlbumRepository): GetAlbumListUseCase = GetAlbumListUseCase(albumRepository)
}