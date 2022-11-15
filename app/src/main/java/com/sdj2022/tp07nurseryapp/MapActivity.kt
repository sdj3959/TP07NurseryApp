package com.sdj2022.tp07nurseryapp

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdj2022.tp07nurseryapp.databinding.ActivityMapBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.util.*

class MapActivity : AppCompatActivity() {

    val binding by lazy {ActivityMapBinding.inflate(layoutInflater)}

    lateinit var latitude:String
    lateinit var longitude:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_brand_24)

        var addr = intent.getStringExtra("addr")

        var geocoder = Geocoder(this, Locale.KOREA)
        var geo = geocoder.getFromLocationName(addr, 1)

        for(i in geo){
            latitude = i.latitude.toString()
            longitude = i.longitude.toString()
        }

        var mapView = MapView(this)
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(latitude.toDouble(), longitude.toDouble()), 2, true)
        var marker = MapPOIItem()
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude.toDouble(), longitude.toDouble())
        marker.itemName = GUserData.userData.get("nursery")
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        mapView.addPOIItem(marker)
        binding.mapView.addView(mapView)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}