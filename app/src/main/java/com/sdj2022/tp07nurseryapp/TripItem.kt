package com.sdj2022.tp07nurseryapp

//https://cliearl.github.io/posts/android/download-json-data-using-retrofit/

data class TripItemResponse constructor(
    var item:MutableList<TripItem>
)

data class TripItem constructor(
    var title:String,
    var spatial:String
)