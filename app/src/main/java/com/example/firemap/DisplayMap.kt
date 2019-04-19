package com.example.firemap


import android.os.Bundle
import android.os.Environment
import android.support.v4.app.FragmentActivity
import android.util.Log

import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_display_map.*
import kotlin.math.abs

class DisplayMap : FragmentActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val PNGPath = Environment.getExternalStorageDirectory().absolutePath + "/Documents/FireMap/PNG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var alat: Double = 39.91722222
        var along: Double = -107.63916667


        var options = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromPath("$PNGPath/temp.png"))
            .positionFromBounds(LatLngBounds(LatLng(alat - 0.013,along - 0.013), LatLng(alat + 0.013, along + 0.013)))
        val blat = 39.92916944444444
        val blong = 07.62437499999999
        mMap.addGroundOverlay(options)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(alat, along), 12.0f))
        map.onResume()
    }
}

