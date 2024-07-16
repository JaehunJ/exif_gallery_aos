package com.example.exif_gallery_aos.domain.album

import com.example.exif_gallery_aos.domain.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * 앨범 리스트 유즈케이스
 */
class GetAlbumListUseCase @Inject constructor(private val repository: AlbumRepository) : BaseFlowUseCase<Unit?, List<AlbumModel>>() {
    override fun execute(params: Unit?): Flow<List<AlbumModel>> = repository.getAlbumList()
}