package com.sdj2022.tp07nurseryapp

class GNurseryData {

    companion object{
        lateinit var nurseryData: HashMap<String, String>
    }
}

class GNurseryDatas constructor(title: String, addr:String, tel:String){
    var nurseryDatas = hashMapOf<String, String>(
        "title" to title,
        "addr" to addr,
        "tel" to tel
    )
}