package com.example.firemap

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat.YV12
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.Toolbar
import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import android.view.SurfaceView
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.MultiDetector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import org.w3c.dom.Text
import java.io.File
import java.io.IOException
import java.net.URI
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*

//import com.google.android.gms.vision.CameraSource

class QR : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        val qrScanBtn = findViewById<Button>(R.id.qrbuttonprocess)
        qrScanBtn.setOnClickListener{
            try{
            scanQR()} catch(e : IOException){
                Toast.makeText(this@QR, "An Error Has Occured In Scanning The Image",Toast.LENGTH_SHORT).show()
            }
        }

        val qrCameraButton = findViewById<Button>(R.id.qrbuttoncamera)
        qrCameraButton.setOnClickListener{
            takePicture()
        }

        val mToolbar: Toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(mToolbar)
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
        when(item?.itemId) {
            R.id.action_qr -> {
            }
        }
        return true
    }

    fun getPublicStorageDir(albumName: String): File {
        val file = File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), albumName)
        return file
    }

    fun createInternalImageF(){
        val fname = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    }
    @Throws(IOException::class)
    fun createImageF(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //val storageDir: File = getPublicStorageDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            val currentPhotoPath = absolutePath
        }
    }

    fun addpic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = getPublicStorageDir("Photos")
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }
    lateinit var qrImgUri : Uri

    fun showPreview(a : Uri){
        val qrImgView = findViewById<ImageView>(R.id.qrImageView)
        val tempbitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(a))
        //JPEG_20190405_173604_8033121832639756727.jpg
        qrImgView.setImageBitmap(tempbitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            addpic()
            showPreview(qrImgUri)
        }
    }

    fun takePicture(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .also {takePictureIntent -> takePictureIntent
                .resolveActivity(packageManager)
                ?.also{
                    val photof: File? = try {
                        createImageF()
                    } catch (ex: IOException) {
                        null
                    }
                    photof?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.example.firemap.fileprovider",
                            it
                        )
                        qrImgUri = photoURI
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
                        startActivityForResult(takePictureIntent, 1)
                        }
                    }
                }
            }

    fun scanQR(){
        val qrImgView = findViewById<ImageView>(R.id.qrImageView)

        val barcodeDetector = BarcodeDetector
            .Builder(this)
            .setBarcodeFormats( Barcode.QR_CODE)
            .build()

        val barCodes = SparseArray<Barcode>(1)

        if(!barcodeDetector.isOperational()){
            Toast.makeText(this@QR, "Could Not Set Up Barcode Detector",Toast.LENGTH_SHORT).show()
        }

        processQR(barcodeDetector)

    }

    fun processQR(a : BarcodeDetector){
        val getbitmap = BitmapFactory.decodeResource(this@QR.resources , R.id.qrImageView)
        if(getbitmap != null){
            val qrFrame = Frame.Builder().setBitmap(getbitmap).build()
            val qrTextView = findViewById<TextView>(R.id.qrtxtContent)
            val barCodes = a.detect(qrFrame)
            val barCode = barCodes.valueAt(0)
            qrTextView.setText(barCode.rawValue)
        }
        else{
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
        }
    }
}
