package com.example.firemap

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import kotlinx.android.synthetic.main.activity_conversion.*

import java.util.ArrayDeque

import org.gdal.gdal.gdal.Translate

private const val READ_REQUEST_CODE: Int = 42

class Conversion : AppCompatActivity() {

    // TODO add TranslateOptions String vector
    // TODO map the functionality to a nicer/more streamlined layout later; get it working first

    private val TAG = "Conversion"  // logging tag

    // TODO add button to choose output directory -> save uri/path

    private val fileURIQueue: ArrayDeque<Uri> = ArrayDeque()  // unknown number of elements

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            performFileSearch()
        }
    }

    private fun performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
        // use 'ACTION_GET_CONTENT' instead?
        // 'apply' is essentially a factory builder
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            DocumentsContract.EXTRA_INITIAL_URI will open the chooser at this initial location

            // Filter to show only pdfs, using the application/pdf data type.
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            type = "*/*"  // TODO limit to pdfs . . . 'x-pdf'?
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData()/getClipData().
            // TODO ensure that output directory has been specified first
            resultData?.clipData?.also { clip ->
                for (idx in 0..clip.itemCount) {
                    Log.i(TAG, "Clip URI: ${clip.getItemAt(idx).uri}")
                    fileURIQueue.add(clip.getItemAt(idx).uri)
                }
            }
            resultData?.data?.also { uri ->
                Log.i(TAG, "URI: $uri")  // getPath()
                fileURIQueue.add(uri)
            }

            if (isExternalStorageWritable()) {
                for (uri in fileURIQueue) {
                    Log.i(TAG, "Write URI: $uri")
                    convertMapPDFToTiff(uri)
                }
                fileURIQueue.clear()  // TODO show queue as ListView/RecycleView on screen
            } else {
                // TODO show on screen - notify
                Log.i(TAG, "External storage not available to write; conversion is impossible")
            }
        } else {
            Log.i(TAG, "Request failed.")  // TODO show on screen - notify
        }
    }

    // TODO add pdf translation here
    // TODO Ensure that conversion CANNOT fail silently - dangerous
    fun convertMapPDFToTiff(uri: Uri) {
//        Translate(dest, uri, opts)
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

}
