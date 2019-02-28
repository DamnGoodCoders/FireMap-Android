package com.example.firemap

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import kotlinx.android.synthetic.main.activity_conversion.*

import org.gdal.gdal.gdal.Translate

private const val READ_REQUEST_CODE: Int = 42

class Conversion : AppCompatActivity() {

    private val TAG = "Conversion"  // logging tag

    // TODO add picker button to choose output directory -> save uri
    // TODO how to choose multiple files?

    private val filename: String = "" // TODO make into arraylist of file names

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            performFileSearch()
        }
    }

    private fun performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            addCategory(Intent.CATEGORY_OPENABLE)

            // Filter to show only pdfs, using the application/pdf data type.
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            type = "*/pdf"  // TODO check to ensure it works correctly
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {

        // The ACTION_GET_CONTENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            resultData?.data?.also { uri ->
                if (isExternalStorageWritable()) {
                    // TODO add pdf translation here
                    convertMapPDFToTiff(uri)
                    // TODO ensure that output directory has been specified first
                    Log.i(TAG, "Uri: $uri")
                } else {
                    Log.i(TAG, "External storage not available")
                }
            }
        }
    }

    fun convertMapPDFToTiff(uri: Uri) {
//        Translate(dest, uri, opts)
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /* Checks if external storage is available to at least read */
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }


}
