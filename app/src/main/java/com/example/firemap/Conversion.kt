package com.example.firemap

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import kotlinx.android.synthetic.main.activity_conversion.*

import java.util.*

import org.gdal.gdal.gdal
import org.gdal.gdal.TranslateOptions

private const val MAP_REQUEST_CODE: Int = 42
private const val DEST_REQUEST_CODE: Int = 73


class Conversion : AppCompatActivity() {

    // TODO map this functionality to a nicer/more streamlined layout later; get it working first

    private val TAG = "Conversion"  // logging tag
    private val defaultDir = "Downloads"  // for use if the destination dir is ever null, which should never happen, but hey
    private var destinationDir: String? = defaultDir  // TODO get default location from Brad
    private val fileURIQueue: ArrayDeque<Uri> = ArrayDeque()  // unknown number of elements
    private val optionsVector: Vector<String> = Vector(4)

    init {
        optionsVector.add("-of")  // output format
        optionsVector.add("GTiff")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)
        setSupportActionBar(toolbar)

        add.setOnClickListener {
            performFileSearch()
        }
        cvt.setOnClickListener {
            convertMapPDFToTiff()
        }
        dst.setOnClickListener {
            getDestinationDir()
        }
    }

    private fun getDestinationDir() {
        // TODO UI: display destination dir in somewhere
        // ACTION_OPEN_DOCUMENT_TREE requires minSdkVersion 21+
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, DEST_REQUEST_CODE)
    }

    private fun performFileSearch() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
        // 'apply' is essentially a factory builder
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            // Filter to only show pdfs that can be "opened"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "application/pdf"
        }

        startActivityForResult(intent, MAP_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // *REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == MAP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document(s) selected by the user won't be returned in the intent.
            // Instead, a URI to each document will be contained in the return intent
            // provided to this method as a parameter.
            resultData?.clipData?.also { clip ->
                for (idx in 0..(clip.itemCount - 1)) {
                    Log.i(TAG, "Clip URI: ${clip.getItemAt(idx).uri}")
                    fileURIQueue.add(clip.getItemAt(idx).uri)
                }
            }
            resultData?.data?.also { uri ->
                Log.i(TAG, "URI: $uri")  // getPath()
                fileURIQueue.add(uri)
            }

        } else if (requestCode == DEST_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                Log.i(TAG, "DESTINTAION URI: $uri")
                if (uri.path != null) {
                    destinationDir = uri.path
                }
            }
        }
        // No need for 'else' statement, the call just went to a different 'intent' action
    }

    // TODO Ensure that conversion CANNOT fail silently - dangerous - use notifyOnFailure
    fun convertMapPDFToTiff() {
        if (isExternalStorageWritable()) {
            if (fileURIQueue.isEmpty()) {
                notifyOnFailure(message = "No files to convert; none selected")
                return
            }
            for (uri in fileURIQueue) {
                Log.i(TAG, "Write URI: $uri")
                Log.i(TAG, "Write path: ${uri.path}")
//                translatePDFToTiff(uri)
            }
            fileURIQueue.clear()  // TODO UI: show queue as ListView/RecycleView on screen; show progress as well
        } else {
            notifyOnFailure(message = "External storage not available to write")
        }
    }

    fun translatePDFToTiff(uri: Uri) {
        // TODO register drivers, create dataset, opts, etc.
        try {
            gdal.AllRegister()
            val ds = gdal.Open(uri.path)
            val filename = uri.lastPathSegment
//            Environment.getExternalStoragePublicDirectory() // gives File
            val options = TranslateOptions(optionsVector)
            gdal.Translate("$destinationDir/$filename", ds, options)
        } catch (e: Exception) {  // hmmmmm...
            notifyOnFailure(message = e.message)
        }
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun notifyOnFailure(title: String? = null, message: String? = null) {
        val fragmentManager = supportFragmentManager
        val failureAlert = FailureAlertDialog()

        if (title != null) {
            failureAlert.setTitle(title)
        }
        if (message != null) {
            failureAlert.setMessage(message)
        }

        failureAlert.show(fragmentManager, "failure_confirmation")
    }

}
