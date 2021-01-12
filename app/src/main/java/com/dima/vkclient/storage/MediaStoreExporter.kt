package com.dima.vkclient.storage

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.dima.vkclient.R
import com.dima.vkclient.common.Constants
import com.dima.vkclient.common.ResourceProvider
import io.reactivex.Observable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MediaStoreExporter(
    private val resourceProvider: ResourceProvider,
    private val applicationContext: Context,
    private val resolver: ContentResolver
) {

    fun saveImage(bitmap: Bitmap): Observable<Unit> {
        return Observable.fromCallable {

            val compressFormat = Bitmap.CompressFormat.JPEG
            val date = System.currentTimeMillis()

            val baseUri =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL
                )
                else MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val relativeLocation =
                "${Environment.DIRECTORY_PICTURES}${File.separator}${resourceProvider.getString(R.string.app_name)}"

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$date.${Constants.IMAGE_EXTENSION}")
                put(MediaStore.MediaColumns.MIME_TYPE, Constants.IMAGE_MIME_TYPE)
                put(MediaStore.MediaColumns.DATE_ADDED, date / 1000)
                put(MediaStore.MediaColumns.SIZE, bitmap.byteCount)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, relativeLocation)
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }

            val newImageUri = resolver.insert(baseUri, contentValues)

            if (newImageUri != null) {

                resolver.openOutputStream(newImageUri, "w").use {
                    bitmap.compress(compressFormat, Constants.IMAGE_QUALITY, it)
                }

                contentValues.clear()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
                    resolver.update(newImageUri, contentValues, null, null)
                }

            } else {
                throw Exception("Image Uri is null")
            }
        }
    }

    fun saveImageInCache(bitmap: Bitmap): Observable<String> {
        return Observable.fromCallable {
            try {

                val imageName = System.currentTimeMillis().toString() + ".jpg"
                val file = File(applicationContext.cacheDir, imageName)

                FileOutputStream(file).use { out ->
                    bitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        Constants.IMAGE_QUALITY,
                        out
                    )
                }

                imageName

            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}