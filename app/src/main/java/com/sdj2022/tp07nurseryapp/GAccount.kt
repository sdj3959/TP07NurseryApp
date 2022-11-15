package com.sdj2022.tp07nurseryapp

data class GAccount constructor(var email:String,
                                  var pw:String,
                                  var nursery:String,
                                  var name:String,
                                  var birth:String?,
                                  var imgUrl:String?,
                                  var position:String,
                                  var nurseryAddr:String,
                                  var nurseryTel:String)
{

//    companion object{
//        var id:String = ""
//        var pw:String = ""
//        var nursery:String = ""
//        var name:String = ""
//        var birth:String = ""
//        var imgUrl:String = ""
//    }
}