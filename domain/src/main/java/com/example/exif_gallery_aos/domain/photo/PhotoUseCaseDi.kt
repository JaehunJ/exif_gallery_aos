package com.example.exif_gallery_aos.domain.photo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoUseCaseProvider {

    @Provides
    @Singleton
    fun provideGetPhotoListUseCase(photoRepository: PhotoRepository) = GetPhotoListUseCase(photoRepository)

    @Provides
    @Singleton
    fun provideGetPhotoUseCase(photoRepository: PhotoRepository) = GetPhotoUseCase(photoRepository)
}