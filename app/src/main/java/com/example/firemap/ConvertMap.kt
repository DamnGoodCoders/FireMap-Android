package com.example.firemap

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ConvertMap : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert_map)
    }
    fun goToDownloadMap(view: View) {
        val intent = Intent(this, DownloadMap::class.java)
        startActivity(intent)
    }
}