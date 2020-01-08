package com.example.quickreport

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.Serializable
import java.lang.Exception
import kotlin.collections.ArrayList

/**
 * Object of Report that holds report related fields
 */

class Report(reportIdField: Int = -1, dateField: String = "", summaryField: String = "",
             titleField: String = "", usernameField: String = "") : Serializable {

    private var reportId = -1
    private var date: String = ""
//    private var col_reportId = -1
    private var weatherId = 0
    private var summary: String = ""
    private var title: String = ""
    private var username: String = ""
    private var imgList = ArrayList<ImgWrapper>()
    private var weather: Weather
    private var partiesInvolvedList = ArrayList<PartiesInvolved>()
//    private var extendedDetails: String? = null

    fun getReportId(): Int {return reportId}
    fun getDate():String{return date}
//    fun getColReportID(): Int{return col_reportId}
    fun getWeatherID(): Int{return weatherId}
    fun getSummary(): String{return summary}
    fun getTitle(): String{return title}
    fun getUsername(): String{return username}
    fun getImageList(): ArrayList<ImgWrapper>{return imgList}
    fun getWeather(): Weather{return weather}
    fun getPartiesInvolved(): ArrayList<PartiesInvolved>{return partiesInvolvedList}

    fun setReportId(id: Int){this.reportId = id}
    fun setDate(date: String){this.date = date}
//    fun setColReportID(id: Int){this.col_reportId = id}
    fun setWeatherID(id: Int){this.weatherId = id}
    fun setSumary(text: String){this.summary = text}
    fun setTitle(title: String){this.title = title}
    fun setUsername(user: String){this.username = user}
    fun setImgList(images: ArrayList<ImgWrapper>){this.imgList = images}
    fun setWeather(weather: Weather){this.weather = weather}
    fun setPartiesInvolved(parties: ArrayList<PartiesInvolved>){this.partiesInvolvedList = parties}

    init{
        this.date = dateField
        this.summary = summaryField
        this.title = titleField
        this.username = usernameField
        this.reportId = reportIdField
        this.weather = Weather() // Placeholder to be overwritten
    }

    fun saveReport(context: Context) {
        val db = DatabaseManager(context)

        deleteSelf(context, true)

        db.insertReport(this)
        this.weather.setDateTime(this.date)
        db.insertWeather(this.weather)
        // Inserts all PartiesInvolved for report
        for (party in this.partiesInvolvedList) {
            party.setDateTime(this.date)
           // if(party.getPartyId() == -1)
                db.insertPartyInvolved(party)
           // else{
               // db.updatePartyInvolved(party)
          //  }

        }
        // Inserts all Images for report
        for (img in this.imgList) {
            img.setDateTime(this.date)
            db.insertImage(img)
        }

        Toast.makeText(context, "Updated Report:$reportId", Toast.LENGTH_SHORT).show()

//        if (newReport) {
//            db.insertReport(this)
//            this.weather.setDateTime(this.date)
//            db.insertWeather(this.weather)it
//            // Inserts all PartiesInvolved for report
//            for (party in this.partiesInvolvedList) {
//                party.setDateTime(this.date)
//                db.insertPartyInvolved(party)
//            }
//            // Inserts all Images for report
//            for (img in this.imgList) {
//                img.setDateTime(this.date)
//                db.insertImage(img)
//            }
//        }
//        else {
//            db.updateReport(this)
//            // Weather doesn't change for a report update
//
//            // Updates all PartiesInvolved for report
//            for (party in this.partiesInvolvedList) {
//                // If new party, insert new
//                if (party.getPartyId() == -1) {
//                    party.setDateTime(this.date)
//                    db.insertPartyInvolved(party)
//                }
//
//                // If old party, update
//                else db.updatePartyInvolved(party)
//            }
//
//            // Updates all Images for report
//            for (img in this.imgList) {
//                // If new image, insert
//                if (img.getPicId() == -1) {
//                    img.setDateTime(this.date)
//                    db.insertImage(img)
//                }
//            }
//        }
    }

    // Load all information for this report from DB and initialize to this instance
    fun loadReport(id: Int, context: Context) {
        val db = DatabaseManager(context)
        try {
            // Load the report object saved in the DB
            var report: Report = db.querySpecifiedReport(id)[0]
            this.reportId =
                report.getReportId() // Possibly redundant, but IDK how IDs are handled DB side
            this.date = report.getDate()
            this.summary = report.getSummary()
            this.title = report.getTitle()
            this.username = report.getUsername()
        }
        catch (e: Exception) {
            Toast.makeText(context, "Report Not Found: $id", Toast.LENGTH_SHORT).show()
            Log.e("Report Not Found:", e.toString())
        }
        try {
            // Load the weather data for the report saved in the DB
            this.weather = db.queryWeather(this.date)[0]
            this.weatherId = this.weather.getWeatherId()

            // Load all the parties involved from the DB
            this.partiesInvolvedList = db.queryParties(this.date)

            // Load all the images for the report save in the DB
            this.imgList = db.queryImages(this.date)

            Toast.makeText(context, "Successfully loaded Report: $id", Toast.LENGTH_SHORT).show()
        }
        catch(e: Exception) {
            Toast.makeText(context, "Error loading Report: $id", Toast.LENGTH_SHORT).show()
            Log.e("Report Loading Error:", e.toString())
        }
    }

    fun deleteSelf(context: Context, silence: Boolean = false) {
        val db = DatabaseManager(context)
        db.deleteReportEntry(this.reportId)
        for (party in this.partiesInvolvedList) { db.deletePartyInvolved(party.getPartyId()) }
        for (image in this.imgList) { db.deleteImage(image.getPicId()) }
        if (!silence) Toast.makeText(context, "Deleted Report:$reportId", Toast.LENGTH_SHORT).show()
    }
}