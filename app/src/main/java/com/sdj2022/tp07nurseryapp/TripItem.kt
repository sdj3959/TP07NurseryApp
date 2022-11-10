package com.sdj2022.tp07nurseryapp

import com.google.gson.annotations.SerializedName

data class ApiResponse constructor(
    @SerializedName("response")
    var response:Response
)

data class Response constructor(
//    @SerializedName("header")
//    var header:MutableList<Header>,
    @SerializedName("body")
    var body:Items
)

//data class Header constructor(
//    @SerializedName("resultCode")
//    var resultCode:String,
//    @SerializedName("resultMsg")
//    var resultMsg:String
//)

data class Items constructor(
    @SerializedName("items")
    var items:Item
)

data class Item constructor(
    @SerializedName("item")
    var item:MutableList<TripItem>
)

data class TripItem constructor(
    @SerializedName("title")
    var title:String,
    @SerializedName("spatial")
    var spatial:String,
    @SerializedName("url")
    var url:String
)