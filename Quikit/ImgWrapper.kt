package com.example.quickreport

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.Icon
import android.util.Base64
import androidx.core.graphics.drawable.toIcon
import java.io.ByteArrayOutputStream
import java.io.Serializable

/**
 * Wrapper class to create image objects associated with images in [SQLiteDatabase]
 */

class ImgWrapper (img: Bitmap, dateTime: String) : Serializable {
    private var picId = -1
    private var image: String
    private var dateTime: String

    init {
        this.image = bitmapToString(img)
        this.dateTime = dateTime
    }

    fun getImage(): Bitmap {
        return stringToBitmap(image)
    }

    fun setImage(img: Bitmap) {
        this.image = bitmapToString(img)
    }

    fun getPicId(): Int{
        return picId
    }

    fun setPicId(id: Int){
        this.picId = id
    }

    fun getDateTime(): String {
        return dateTime
    }

    fun setDateTime(dateTime: String) {
        this.dateTime = dateTime
    }

    fun bitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    fun stringToBitmap(encodedString: String): Bitmap {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            BitmapFactory.decodeByteArray(encodedString.toByteArray(), 0, encodedString.toByteArray().size)
        }
    }

    fun resizeBitmap(bitmap: Bitmap, prefWidth: Float, prefHeight: Float): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = prefWidth / width
        val scaleHeight = prefHeight / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        val resizedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, width, height, matrix, false
        )
        return resizedBitmap
    }
}