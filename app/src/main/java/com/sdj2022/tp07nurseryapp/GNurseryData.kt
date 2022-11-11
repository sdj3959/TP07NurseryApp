package com.sdj2022.tp07nurseryapp

class GNurseryData {

    companion object{

        var data = mutableListOf<NurseryData>()
    }
}

class NurseryData {
    var title:String = ""
    var addr:String = ""
    var tel:String = ""
}