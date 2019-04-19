package com.example.firemap
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import org.w3c.dom.Text
import android.content.res.AssetManager
import android.support.v4.app.ActivityCompat
import android.util.Log
import java.io.File
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private val globals = ApplicationGlobals()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mToolbar: Toolbar = findViewById(R.id.toolbar)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

        setSupportActionBar(mToolbar)

    }
    fun goToSelectMap(view: View) {
        val intent = Intent(this, SelectMap::class.java)
        startActivity(intent)
    }

    fun goToDisplayMap(view: View) {
        val intent = Intent(this, DisplayMap::class.java)
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.my_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var selectedOption = ""
        when(item?.itemId) {
            R.id.action_change_map -> {
                val intent = Intent(this, SelectMap::class.java)
                startActivity(intent)
            }
        }

        return true
    }

    override fun onResume() {
        super.onResume()
        try {
            Log.e("Global Lat Long", globals.getLatLongPlacement().toString())
        } catch (e: Exception) {
            Log.e("err","No lat long globally (yet)")
        }
    }

}
