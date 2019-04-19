package com.example.firemap


import android.app.Application
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class ApplicationGlobals : Application() {

    private lateinit var FileList: ArrayList<String>
    fun getMyDataset(): ArrayList<String> {
        return FileList
    }
    fun setMyDataset(DS: ArrayList<String>) {
        FileList = DS
    }

    private lateinit var selectedFile: String

    fun getSelectedFile() = selectedFile
    fun setSelectedFile(sf: String) {
        selectedFile = sf
    }
    private lateinit var selectedFileAsPNG: String
    fun getSelectedFileAsPNG() = selectedFileAsPNG
    fun setSelectedFileAsPNG(sf: String) {
        selectedFileAsPNG = sf
    }
    private lateinit var latLongPlacement: LatLngBounds
    fun getLatLongPlacement() = latLongPlacement
    fun setLatLongPlacement(llb: LatLngBounds) {
        latLongPlacement = llb
    }
}