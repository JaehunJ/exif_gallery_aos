package com.example.exif_gallery_aos.presentation

sealed class Page(val route:RouteName){
    object AlbumGrid:Page(RouteName.AlbumGrid)
    object PhotoGrid:Page(RouteName.PhotoGrid)
    object ImageView:Page(RouteName.ImageView)

    enum class RouteName{
        AlbumGrid,
        PhotoGrid,
        ImageView,
    }
}

