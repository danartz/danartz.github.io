package com.example.quickreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.report_image_view.*

class ReportImageView : AppCompatActivity() {
    private val PREF_WIDTH = 956.toFloat()
    private val PREF_HEIGHT = 1500.toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.report_image_view)
        var context = this
        val WIDTH = 401.toFloat()
        val HEIGHT = 547.toFloat()

        var imageID: Int = intent.getIntExtra("ImageID", -1)
        var report: Report = intent.getSerializableExtra("Report") as Report
        var imageIDForDeletion: Int = intent.getIntExtra("ImageIDForDelete", -1)

        var imgList: ArrayList<ImgWrapper> = report.getImageList()

        imageView.setImageBitmap(imgList[imageID].resizeBitmap(imgList[imageID].getImage(), PREF_WIDTH, PREF_HEIGHT))

        deleteButton.setOnClickListener{
            imgList.removeAt(imageID)
            report.setImgList(imgList)
            val db = DatabaseManager(context)

            db.deleteImage(imageIDForDeletion)
            Log.d("delete image : ", imageID.toString() + " Submitted Successfully")
            handleExit(report)
        }
        backButton.setOnClickListener{
            handleExit(report)
        }
    }


    private fun handleExit(report: Report){
        val intent = Intent(this, ImageListView ::class.java)
        intent.putExtra("Report", report)
        startActivity(intent)
    }


}
