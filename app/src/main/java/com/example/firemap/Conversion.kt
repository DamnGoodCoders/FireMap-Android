package com.example.firemap

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_conversion.*

private const val READ_REQUEST_CODE: Int = 42

class Conversion : AppCompatActivity() {

    // TODO add picker to choose output directory

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

            // Filter to show only images, using the image MIME data type.
            // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            type = "*/pdf"  // TODO check to ensure it works correctly
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
            // Pull that URI using resultData.getData().
            resultData?.data?.also { uri ->
//                Log.i(TAG, "Uri: $uri")
//                showImage(uri)
            }
        }
    }

}
