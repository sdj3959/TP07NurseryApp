package com.sdj2022.tp07nurseryapp

data class TripItemResponse constructor(
    var body:MutableList<TripItem>
)

data class TripItem constructor(
    var title:String,
    var spatial:String
)