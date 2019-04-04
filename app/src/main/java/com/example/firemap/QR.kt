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
import android.provider.MediaStore
import android.util.SparseArray
import android.view.SurfaceView
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.MultiDetector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.nio.ByteBuffer

//import com.google.android.gms.vision.CameraSource

class QR : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)
        val qrScanBtn = findViewById<Button>(R.id.qrbuttonprocess)
        qrScanBtn.setOnClickListener{
            Toast.makeText(this@QR, "Processing Image", Toast.LENGTH_SHORT).show()
            scanQR()
        }
    }

    fun scanQR(){
        val qrImgView = findViewById<ImageView>(R.id.qrImageView)
        val qrBitMap = findViewById<ImageView>(R.id.qrImageView)

        val barcodeDetector = BarcodeDetector
            .Builder(this)
            .setBarcodeFormats( Barcode.QR_CODE)
            .build()

        fun TakePicture(){
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .also {takePictureIntent -> takePictureIntent
                    .resolveActivity(packageManager)
                    ?.also{
                startActivityForResult(takePictureIntent,1)
            }}
        }

        fun TranslatePDF(){

        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            if(requestCode == 1 && resultCode == RESULT_OK){
                val tempbitmap = data.extras.get("data") as Bitmap
                qrImgView.setImageBitmap(tempbitmap)
            }
        }


        //val qrFrame = Frame.Builder().setImageData(ByteBuffer.allocate(5000),1080,1024, YV12).build()
        val qrFrame = Frame.Builder().setBitmap(qrBitMap).build()

        //qrImgView.setImageBitmap(qrBitMap)

        val barCodes = barcodeDetector.detect()
        val tempbarCodes = barCodes.valueAt(0)
        val qrtextview = findViewById<TextView>(R.id.qrtxtContent)

        qrtextview.text = tempbarCodes.rawValue

        if(!barcodeDetector.isOperational()){
            Toast.makeText(this@QR, "Could Not Set Up Barcode Detector",Toast.LENGTH_SHORT).show()
        }

        //barcodeDetector.se
        //CameraSource.Builder
        //val tempQRCamera = CameraSource.Builder(this,barcodeDetector).setAutoFocusEnabled(true).setFacing(1).build()
        //tempQRCamera.takePicture()
    }

    fun takePicture(a: BarcodeDetector){
        val tempQRCamera = CameraSource.Builder(this,a).setAutoFocusEnabled(true).setFacing(1).build()
        checkSelfPermission(Context.CAMERA_SERVICE)
        tempQRCamera.start()
    }
}
