package com.example.firemap;

import android.util.Log;
import org.beyka.tiffbitmapfactory.*;


public class FileConverter {
    private IProgressListener progressListener = new IProgressListener() {
        @Override
        public void reportProgress(long processedPixels, long totalPixels) {
            Log.v("Progress reporter", String.format("Processed %d pixels from %d", processedPixels, totalPixels));
        }
    };

    public void convertFile(String in, String out) {
        TiffConverter.ConverterOptions options = new TiffConverter.ConverterOptions();
        options.throwExceptions = false; //Set to true if you want use java exception mechanism;
        options.availableMemory = 128 * 1024 * 1024; //Available 128Mb for work;
        options.readTiffDirectory = 1; //Number of tiff directory to convert;
        Log.i("TIFF File: ", in);
        Log.i("PNG File:", out);
        Boolean success = TiffConverter.convertTiffPng(in, out, options, progressListener);
        Log.i("Conversion succeeded", success.toString());
    }
}