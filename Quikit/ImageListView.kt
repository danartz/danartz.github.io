package com.example.quickreport

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ImageWriter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.image_list_view.*
import android.view.ViewGroup
import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.drawable.toIcon
import androidx.recyclerview.widget.RecyclerView
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.image_list_view.appTitle
import kotlinx.android.synthetic.main.image_list_view.back
import kotlinx.android.synthetic.main.image_list_view.next
import kotlinx.android.synthetic.main.reportlistview.*

class ImageListView : AppCompatActivity() {

    private val PREF_WIDTH = 788.toFloat()
    private val PREF_HEIGHT = 1400.toFloat()

    private lateinit var report: Report
    private lateinit var images: ArrayList<ImgWrapper>
    private var imageIndex = 0 // The starting point of the currently displayed reports
    private var totalImages = 0

    private fun load5Images(): Array<ImgWrapper> {
        var toReturn: ArrayList<ImgWrapper> = ArrayList(0)
        for (i in this.imageIndex until this.imageIndex + 5) {
            if (images.size == i)
                break
            toReturn.add(images[i])
        }

        return toReturn.toTypedArray()
    }

    private fun updateDisplay(imgs: Array<ImageView>) {

        // Load the initial reports
        val reportsList: Array<ImgWrapper> = load5Images() // Load the next 5 reports from this.reportIndex to this.reportIndex + 4

        // Determine if the 'Next' button needs to be visible ( remaining reports > 5 )
        if (this.totalImages - this.imageIndex > 5) {
            next.isVisible = true
            next.isClickable = true
        }
        else {
            next.isVisible = false
            next.isClickable = false
        }

        // Determine if the 'Prev' button needs to be visible ( this.reportIndex > 0 )
        if (this.imageIndex > 0) {
            previous.isVisible = true
            previous.isClickable = true
        }
        else {
            previous.isVisible = false
            previous.isClickable = false
        }

        // Set all report displays to not be visible before updating
        for (image in 0 until 5) {
            imgs[image].isVisible = false
        }

        // Update info for each needed report display then toggle back to visible
        for (image in 0 until reportsList.size) {
            // Sets the text to a format for the summary. This should wrap text... but we'll see in time if that's true
            imgs[image].setImageBitmap(images[image+imageIndex].getImage())
            var imageID = images[image].getPicId()
            imgs[image].setOnClickListener {
                val intent = Intent(this, ReportImageView ::class.java)
                intent.putExtra("Report", this.report)
                intent.putExtra("ImageID", image + this.imageIndex)
                intent.putExtra("ImageIDForDelete", image + imageID)
                startActivity(intent)
            }

            imgs[image].isVisible = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_list_view)

        appTitle.setOnClickListener {
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }

        this.report = intent.getSerializableExtra("Report") as Report

        reportIDtext.text = String.format("ID:%s\tDate:%s",report.getReportId().toString(), report.getDate())

        back.setOnClickListener {
            val intent = Intent(this, ViewEditReport ::class.java)
            intent.putExtra("CreatingNew", false)
            intent.putExtra("Report", this.report)
            startActivity(intent)
        }

        // Collect the existing images for the report
        this.images = report.getImageList()
        var imageViews = arrayOf(imageView0, imageView1, imageView2, imageView3, imageView4)
        this.totalImages = this.images.size

        updateDisplay(imageViews)

        next.setOnClickListener {
            this.imageIndex += 5
            updateDisplay(imageViews)
        }

        previous.setOnClickListener {
            this.imageIndex -= 5
            updateDisplay(imageViews)
        }

    }
}
