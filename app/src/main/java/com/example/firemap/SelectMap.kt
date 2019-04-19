package com.example.firemap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.android.synthetic.main.activity_select_map.*
import java.io.File
import org.gdal.gdal.Dataset
import org.gdal.gdal.gdal

class SelectMap : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var filelist = ArrayList<String>()
    private var TIFFPath = Environment.getExternalStorageDirectory().absolutePath + "/Documents/FireMap/TIFF"
    private var PNGPath = Environment.getExternalStorageDirectory().absolutePath + "/Documents/FireMap/PNG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_map)
        gdal.AllRegister()
        var TiffFiles = ArrayList<String>()
        var tiffPathFileType: File = File(TIFFPath)
        if(tiffPathFileType.length() > 0) {
            for (file: String in tiffPathFileType!!.list()) {
                filelist.add(file)
            }
        } else {
            filelist.add("Sorry.  No files are found in /Documents/FireMap/TIFF")
        }
        Log.e("Files: ", filelist.toString())
        viewManager = LinearLayoutManager(this)
        viewAdapter = DirectoryAdapter(filelist)
        recyclerView = fileRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    class DirectoryAdapter(private val filelist: ArrayList<String>) : RecyclerView.Adapter<DirectoryAdapter.FileViewHolder>() {
        private val TIFFPath = Environment.getExternalStorageDirectory().absolutePath + "/Documents/FireMap/TIFF"
        private val PNGPath = Environment.getExternalStorageDirectory().absolutePath + "/Documents/FireMap/PNG"
        private val applicationGlobals = ApplicationGlobals()

        class FileViewHolder(val fileButton: Button) : RecyclerView.ViewHolder(fileButton)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryAdapter.FileViewHolder {
            val fileButton = LayoutInflater.from(parent.context).inflate(R.layout.file_view, parent, false) as Button
            if(filelist[0] != "Sorry.  No files are found in /Documents/FireMap/TIFF" ) {
                fileButton.setOnClickListener {
                    applicationGlobals.setSelectedFile(fileButton.text.toString())
                    val fileConverter = FileConverter()
//                    fileConverter.convertFile(
//                        TIFFPath + "/" + applicationGlobals.getSelectedFile(),
//                        "$PNGPath/temp.png"
//                    )
                    applicationGlobals.setSelectedFileAsPNG("$PNGPath/temp.png")
                    val ds = gdal.Open("$TIFFPath" + "/" + applicationGlobals.getSelectedFile())
                    val width = ds.rasterXSize
                    val height = ds.rasterYSize
                    Log.i("GDAL Info", gdal.GDALInfo(ds, null))
                    val lines = gdal.GDALInfo(ds, null).split("\n")
                    var upperLeft = String()
                    var lowerLeft = String()
                    var upperRight = String()
                    var lowerRight = String()
                    for (i in 0 until lines.size) {
                        if (lines[i].contains("Corner Coordinates")) {
                            upperLeft = lines[i + 1]
                            lowerLeft = lines[i + 2]
                            upperRight = lines[i + 3]
                            lowerRight = lines[i + 4]
                        }
                    }

                    val SWFMCoord = FMCoordinate(lowerLeft)
                    val NEFMCoord = FMCoordinate(upperRight)

                    val SWLong = DMS(SWFMCoord.getLatitude()!!)
                    val SWLat = DMS(SWFMCoord.getLongitude()!!)
                    val NELong = DMS(NEFMCoord.getLatitude()!!)
                    val NELat = DMS(NEFMCoord.getLongitude()!!)

                    val bounds = LatLngBounds(LatLng(SWLat.getDecimal(), SWLong.getDecimal()), LatLng(NELat.getDecimal(), NELong.getDecimal()))
                    applicationGlobals.setLatLongPlacement(bounds)
                }
            } else {
                Log.e("Error: ", "No TIFFs found in /Documents/FireMap/TIFF")
            }
            return FileViewHolder(fileButton)
        }

        override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
            holder.fileButton.text = filelist[position]
        }

        override fun getItemCount() = filelist.size
    }
}