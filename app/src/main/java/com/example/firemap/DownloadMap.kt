package com.example.firemap

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebViewClient
import com.example.firemap.R
import kotlinx.android.synthetic.main.activity_download_map.*



class DownloadMap : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_map)
        var url = "https://ftp.nifc.gov/public/incident_specific_data/rocky_mtn/2018/Cabin_Lake/GIS/products"

        webView.loadUrl(url)
        webView.webViewClient = WebViewClient()

        webView.setDownloadListener { url, userAgent, contentDisposition, mimeType, _ ->
            // Gets filename based on url
            val filename = URLUtil.guessFileName(url, contentDisposition, mimeType)

            // Prompts download:
            val builder = AlertDialog.Builder(this@DownloadMap)
            builder.setTitle("Download")
            builder.setMessage("Do you want to save $filename")
            builder.setPositiveButton("Yes") { _, _ ->
                val request = DownloadManager.Request(Uri.parse(url))
                val cookie = CookieManager.getInstance().getCookie(url)
                request.addRequestHeader("Cookie", cookie)
                request.addRequestHeader("User-Agent", userAgent)
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS + "/FireMap/PDF", filename)
                downloadManager.enqueue(request)
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            val dialog: AlertDialog = builder.create()

            dialog.show()
        }
    }

}
