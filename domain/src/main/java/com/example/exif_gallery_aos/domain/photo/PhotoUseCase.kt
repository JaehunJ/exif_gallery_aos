package com.example.exif_gallery_aos.domain.photo

import com.example.exif_gallery_aos.domain.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoListUseCase @Inject constructor(private val photoRepository: PhotoRepository) :
    BaseFlowUseCase<GetPhotoListUseCase.Params, List<PhotoModel>>() {
    data class Params(val albumId: Int)

    override fun execute(params: Params) = photoRepository.getPhotoList(params.albumId)

}

class GetPhotoUseCase @Inject constructor(private val photoRepository: PhotoRepository):BaseFlowUseCase<GetPhotoUseCase.Params, PhotoModel>(){
    data class Params(val photoId: Int)

    override fun execute(params: Params) = photoRepository.getPhoto(params.photoId)
}

class GetPhotoExifUseCase @Inject constructor(private val photoRepository: PhotoRepository):BaseFlowUseCase<GetPhotoExifUseCase.Params, ExifModel>(){
    data class Params(val path:String)

    override fun execute(params: Params) = photoRepository.getPhotoExif(params.path)
}