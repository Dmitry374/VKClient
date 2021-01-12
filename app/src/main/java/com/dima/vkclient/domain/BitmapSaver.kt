package com.dima.vkclient.domain

import android.graphics.Bitmap
import com.dima.vkclient.storage.MediaStoreExporter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BitmapSaver(
    private val mediaStoreExporter: MediaStoreExporter
) {

    fun saveImage(bitmap: Bitmap): Observable<Unit> {
        return mediaStoreExporter.saveImage(bitmap)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveImageInCache(bitmap: Bitmap): Observable<String> {
        return mediaStoreExporter.saveImageInCache(bitmap)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }
}