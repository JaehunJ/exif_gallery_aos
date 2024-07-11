package com.example.exif_gallery_aos.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

abstract class BaseFlowUseCase<in P, R>{
    operator fun invoke(params:P):Flow<R>{
        return try {
            execute(params)
        }catch (e:Exception){
            flowOf()
        }
    }

    protected abstract fun execute(params:P):Flow<R>
}