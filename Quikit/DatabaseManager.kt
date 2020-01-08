package com.example.quickreport

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.Serializable
import java.util.ArrayList


/**
 * A manager class for the SQLite Database
 *
 * This class includes queries, insertion, deletions and updates for the database.
 * If a database doesn't exist, one will be created.
 * @param context

 */


class DatabaseManager (var context: Context) : SQLiteOpenHelper(context, databaseName, null, 2){

    /**
     * Creates a [SQLiteDatabase] table if one doesn't already exist
     *
     */
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_REPORT)
    }

    /**
     * If the database version changes, the database is recreated
     *
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_PARTIES)
        db?.execSQL(SQL_DELETE_REPORT)
        onCreate(db)
    }

    @Throws (SQLiteConstraintException::class)
            /**
             * Inserts [report] object into database
             * @returns true if created successfully
             */
    fun insertReport(report: Report): Boolean{
        //Gets the data repository in write mode
        val db = writableDatabase

        val values = ContentValues()
        try {
            values.put(DBContract.ReportEntry.getColDate(), report.getDate())
            Log.d("Should be parties", DBContract.PartiesInvolvedEntry.getTablePartiesInvolved())
            values.put(DBContract.ReportEntry.getColTitle(), report.getTitle())
            values.put(DBContract.ReportEntry.getColUsername(), report.getUsername())
            values.put(DBContract.ReportEntry.getColSummary(), report.getSummary())
            val result = db.insert(DBContract.ReportEntry.getTableReport(), null, values)
            if(result == -1.toLong())
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

        } catch (e : SQLiteException) {
            createAllTables(db)
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    @Throws (SQLiteConstraintException::class)
    /**
     * Updates [report] object into database
     * @returns true if created successfully
     */

    fun updateReport(report: Report): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        try {
            values.put(DBContract.ReportEntry.getColUsername(), report.getUsername())
            values.put(DBContract.ReportEntry.getColTitle(), report.getTitle())
            values.put(DBContract.ReportEntry.getColSummary(), report.getSummary())
            val result = db.update(
                DBContract.ReportEntry.getTableReport(), values,
                DBContract.ReportEntry.getColReportId() +
                        "=?", arrayOf(report.getReportId().toString())
            )
            if(result == -1)
                Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT).show()
        }catch(e: SQLiteException){
            return false
        }

        db.close()
        return true
    }

    @Throws (SQLiteConstraintException::class)
    /**
     * Updates [report] object into database
     * @returns true if created successfully
     */
    fun updatePartyInvolved(party: PartiesInvolved): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        try{
            values.put(DBContract.PartiesInvolvedEntry.getCarMake(), party.getCarMake())
            values.put(DBContract.PartiesInvolvedEntry.getCarModel(), party.getCarModel())
            values.put(DBContract.PartiesInvolvedEntry.getLicensePlate(), party.getLisencePlate())
            values.put(DBContract.PartiesInvolvedEntry.getFirstName(), party.getFirstName())
            values.put(DBContract.PartiesInvolvedEntry.getLastName(), party.getLastName())
            values.put(DBContract.PartiesInvolvedEntry.getSsn(), party.getSsn())
            values.put(DBContract.PartiesInvolvedEntry.getPhone(), party.getPhoneNum())
            values.put(DBContract.PartiesInvolvedEntry.getAddress(), party.getHomeAddress())
            values.put(DBContract.PartiesInvolvedEntry.getState(), party.getState())
            values.put(DBContract.PartiesInvolvedEntry.getCity(), party.getCity())
            val result = db.update(DBContract.PartiesInvolvedEntry.getTablePartiesInvolved(),
                values, DBContract.PartiesInvolvedEntry.getPartyId() + "=?",
                arrayOf(party.getPartyId().toString()))
            if(result == -1)
                Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Update Success", Toast.LENGTH_SHORT).show()

        }catch(e: SQLiteException){
            return false
        }
        return true
    }

    @Throws (SQLiteConstraintException::class)
            /**
             * Inserts [PartiesInvolved] object into database's PartiesInvolved table
             * @returns true if created successfully
             */
    fun insertPartyInvolved(partyInvolved: PartiesInvolved): Boolean{
        //Gets the data repository in write mode
        val db = writableDatabase
        val values = ContentValues()
        try {
            values.put(DBContract.PartiesInvolvedEntry.getCarModel(), partyInvolved.getCarModel())
            values.put(DBContract.PartiesInvolvedEntry.getCarMake(), partyInvolved.getCarMake())
            values.put(DBContract.PartiesInvolvedEntry.getLicensePlate(), partyInvolved.getLisencePlate())
            values.put(DBContract.PartiesInvolvedEntry.getFirstName(), partyInvolved.getFirstName())
            values.put(DBContract.PartiesInvolvedEntry.getLastName(), partyInvolved.getLastName())
            values.put(DBContract.PartiesInvolvedEntry.getSsn(), partyInvolved.getSsn())
            values.put(DBContract.PartiesInvolvedEntry.getPhone(), partyInvolved.getPhoneNum())
            values.put(DBContract.PartiesInvolvedEntry.getAddress(), partyInvolved.getHomeAddress())
            values.put(DBContract.PartiesInvolvedEntry.getState(), partyInvolved.getState())
            values.put(DBContract.PartiesInvolvedEntry.getCity(), partyInvolved.getCity())
            values.put(DBContract.PartiesInvolvedEntry.getDateTime(), partyInvolved.getDateTime())
            val result = db.insert(DBContract.PartiesInvolvedEntry.getTablePartiesInvolved(), null, values)
            if(result == -1.toLong())
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

        } catch (e : SQLiteException) {
            createAllTables(db)
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            return false
        }
        db.close()
        return true
    }

    @Throws (SQLiteConstraintException::class)
            /**
             * Inserts [Weather] object into database's Weather table
             * @returns true if created successfully
             */
    fun insertWeather(weather: Weather): Boolean{
        //Gets the data repository in write mode
        val db = writableDatabase
        val values = ContentValues()
        try {
            values.put(DBContract.WeatherEntry.getLocation(), weather.getLocation())
            values.put(DBContract.WeatherEntry.getCondition(), weather.getCondition())
            values.put(DBContract.WeatherEntry.getWindSpeed(), weather.getWind())
            values.put(DBContract.WeatherEntry.getWindDir(), weather.getWindDir())
            values.put(DBContract.WeatherEntry.getDateTime(), weather.getDateTime())
            values.put(DBContract.WeatherEntry.getTemp(), weather.getTemp())
            val result = db.insert(DBContract.WeatherEntry.getTableWeather(), null, values)
            if(result == -1.toLong())
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

        } catch (e : SQLiteException) {
            createAllTables(db)
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            return false
        }
        db.close()
        return true
    }

    @Throws (SQLiteConstraintException::class)
            /**
             * Inserts [ImageWrapper] object into database's Weather table
             * @returns true if created successfully
             */
    fun insertImage(image: ImgWrapper): Boolean{
        //Gets the data repository in write mode
        val db = writableDatabase
        val values = ContentValues()
        try {
            var stream = ByteArrayOutputStream()
            image.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream)
            values.put(DBContract.ImageEntry.getImgSrc(), image.bitmapToString(image.getImage()))
            values.put(DBContract.ImageEntry.getDateTime(), image.getDateTime())
            val result = db.insert(DBContract.ImageEntry.getTableImages(), null, values)
            if(result == -1.toLong())
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

        } catch (e : SQLiteException) {
            createAllTables(db)
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            return false
        }
        db.close()
        return true
    }

    /**
     * Performs a select all query and returns an ArrayList of ReportSummary objects
     * @return [ReportSummary] Arraylist
     */
    fun queryAllSummary(): ArrayList<ReportSummary>{
        val db = readableDatabase
        val reportSummaries = ArrayList<ReportSummary>()
        var cursor: Cursor? = null
        //
//         dropTables(db)
        try {
            cursor = db.rawQuery("select * from " + DBContract.ReportEntry.getTableReport(), null)
        } catch (e : SQLiteException) {
            createAllTables(db)
            return ArrayList()
        }

        var date: String
        var summary: String
        var reportId: Int
        var userName: String
        var title: String
        if (cursor!!.moveToFirst()){
            while (!cursor.isAfterLast){
                reportId = cursor.getInt(cursor.getColumnIndex(DBContract.ReportEntry.getColReportId()))
                date = cursor.getString(cursor.getColumnIndex(DBContract.ReportEntry.getColDate()))
                summary = cursor.getString(cursor.getColumnIndex(DBContract.ReportEntry.getColSummary()))
                title = cursor.getString(cursor.getColumnIndex(DBContract.ReportEntry.getColTitle()))
                userName = cursor.getString(cursor.getColumnIndex(DBContract.ReportEntry.getColUsername()))
                reportSummaries.add(ReportSummary(date, summary, reportId, title, userName))
                cursor.moveToNext()
            }
        }
        return reportSummaries
    }

    /**
     * Performs a select all query and returns an ArrayList of Weather objects
     * The list will only contain 1 weather object as each report only has 1 weather object
     * associated with it.
     * @return [Weather] Arraylist
     */
    fun queryWeather(dateTime: String): ArrayList<Weather>{
        val db = readableDatabase
        val weather = ArrayList<Weather>()
        var cursor: Cursor? = null
        //
//        dropTables(db)
        try {
            cursor = db.rawQuery("select * from " + DBContract.WeatherEntry.getTableWeather() +
                    " WHERE " + DBContract.WeatherEntry.getDateTime() + " = ?", arrayOf(dateTime))
        } catch (e : SQLiteException) {
            createAllTables(db)
            return ArrayList()
        }

        var location: String
        var condition: String
        var wind: String
        var windDir: String
        var dateTime: String
        var temp: String
        var weatherId: Int

        if (cursor!!.moveToFirst()){
            while (!cursor.isAfterLast){
                dateTime = cursor.getString(cursor.getColumnIndex(DBContract.WeatherEntry.getDateTime()))
                location = cursor.getString(cursor.getColumnIndex(DBContract.WeatherEntry.getLocation()))
                condition = cursor.getString(cursor.getColumnIndex(DBContract.WeatherEntry.getCondition()))
                wind = cursor.getString(cursor.getColumnIndex(DBContract.WeatherEntry.getWindSpeed()))
                windDir = cursor.getString(cursor.getColumnIndex(DBContract.WeatherEntry.getWindDir()))
                temp = cursor.getString(cursor.getColumnIndex(DBContract.WeatherEntry.getTemp()))
                weatherId = cursor.getInt(cursor.getColumnIndex(DBContract.WeatherEntry.getWeatherId()))
                weather.add(Weather("", "", location))
                weather[0].setDateTime(dateTime)
                weather[0].setCondition(condition)
                weather[0].setWind(wind)
                weather[0].setWindDir(windDir)
                weather[0].setTemp(temp)
                weather[0].setWeatherID(weatherId)

                cursor.moveToNext()
            }
        }
        return weather
    }

    fun querySpecifiedReport(reportId: Int): ArrayList<Report>{
        val db = readableDatabase
        val report = ArrayList<Report>()
        var cursor: Cursor? = null
        //
//         dropTables(db)
        try {
            cursor = db.rawQuery("select * from " + DBContract.ReportEntry.getTableReport() +
            " WHERE " + DBContract.ReportEntry.getColReportId() + " = ?", arrayOf(reportId.toString()))
        } catch (e : SQLiteException) {
            createAllTables(db)
            return ArrayList()
        }

        var date: String
        var summary: String
        var title: String
        var username: String
        var reportId: Int

        if (cursor!!.moveToFirst()){
            while (!cursor.isAfterLast){
                date = cursor.getString(cursor.getColumnIndex(DBContract.ReportEntry.getColDate()))
                summary = cursor.getString(cursor.getColumnIndex(DBContract.ReportEntry.getColSummary()))
                title = cursor.getString(cursor.getColumnIndex(DBContract.ReportEntry.getColTitle()))
                username = cursor.getString(cursor.getColumnIndex(DBContract.ReportEntry.getColUsername()))
                reportId = cursor.getInt(cursor.getColumnIndex(DBContract.ReportEntry.getColReportId()))

                report.add(Report(reportId, date, summary, title, username))
                cursor.moveToNext()
            }
        }
        return report
    }

    /**
     * Returns ArrayList of imgWrappers for a specified report
     * @return [ImgWrapper] ArrayList
     */
    fun queryImages(reportDate: String): ArrayList<ImgWrapper>{
        val db = readableDatabase
        val imgList = ArrayList<ImgWrapper>()
        var cursor: Cursor? = null
        //
//         dropTables(db)
        try {
            cursor = db.rawQuery("select * from " + DBContract.ImageEntry.getTableImages() +
                    " WHERE " + DBContract.ImageEntry.getDateTime() + " = ?", arrayOf(reportDate))
        } catch (e : SQLiteException) {
            createAllTables(db)
            return ArrayList()
        }

        var date: String
        var picId: Int
        var imgSrc: String

        if (cursor!!.moveToFirst()){
            while (!cursor.isAfterLast){
                date = cursor.getString(cursor.getColumnIndex(DBContract.ImageEntry.getDateTime()))
                picId = cursor.getInt(cursor.getColumnIndex(DBContract.ImageEntry.getImgId()))
                imgSrc = cursor.getString(cursor.getColumnIndex(DBContract.ImageEntry.getImgSrc()))

                // Adapted from ImgWrapper
                var imgBMP: Bitmap
                try {
                    val encodeByte =
                        Base64.decode(imgSrc, Base64.DEFAULT)
                    imgBMP = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                } catch (e: Exception) {
                    e.message
                    imgBMP = BitmapFactory.decodeByteArray(imgSrc.toByteArray(), 0, imgSrc.toByteArray().size)
                }

                var img = ImgWrapper(imgBMP, date)
                img.setPicId(picId)
                imgList.add(img)
                cursor.moveToNext()
            }
        }
        return imgList
    }

    /**
     * Returns ArrayList of parties involved with a specified report
     * @return [PartiesInvolved] ArrayList
     */
    fun queryParties(reportDate: String): ArrayList<PartiesInvolved>{
        val db = readableDatabase
        val partiesList = ArrayList<PartiesInvolved>()
        var cursor: Cursor? = null
        //
//         dropTables(db)
        try {
            cursor = db.rawQuery("select * from " + DBContract.PartiesInvolvedEntry.getTablePartiesInvolved() +
                " where " + DBContract.PartiesInvolvedEntry.getDateTime() + " = ?", arrayOf(reportDate))
        } catch (e : SQLiteException) {
            createAllTables(db)
            return ArrayList()
        }

        var carMake: String
        var carModel: String
        var lisencePlate: String
        var firstName: String
        var lastName: String
        var ssn: Long
        var phoneNum: Long
        var homeAddress: String
        var state: String
        var city: String
        var dateTime: String
        var partyId: Int

        if (cursor!!.moveToFirst()){
            while (!cursor.isAfterLast){
                carMake= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getCarMake()))
                carModel= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getCarModel()))
                lisencePlate= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getLicensePlate()))
                firstName= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getFirstName()))
                lastName= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getLastName()))
                ssn= cursor.getLong(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getSsn()))
                phoneNum= cursor.getLong(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getPhone()))
                homeAddress= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getAddress()))
                state= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getState()))
                city= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getCity()))
                dateTime= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getDateTime()))
                partyId = cursor.getInt(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getPartyId()))

                var party = PartiesInvolved(dateTime, carMake, carModel, lisencePlate, firstName, lastName,
                    ssn, phoneNum, homeAddress, state, city)
                party.setPartyId(partyId)
                partiesList.add(party)
                cursor.moveToNext()
            }
        }
        return partiesList
    }
    
    fun queryAllPartiesTableTest(reportDate: String): ArrayList<PartiesInvolved>{
        val db = readableDatabase
        val partiesList = ArrayList<PartiesInvolved>()
        var cursor: Cursor? = null
        //
         dropTables(db)
        try {
            cursor = db.rawQuery("select * from " + DBContract.PartiesInvolvedEntry.getTablePartiesInvolved(), null)
        } catch (e : SQLiteException) {
            createAllTables(db)
            return ArrayList()
        }

        var carMake: String
        var carModel: String
        var lisencePlate: String
        var firstName: String
        var lastName: String
        var ssn: Long
        var phoneNum: Long
        var homeAddress: String
        var state: String
        var city: String
        var dateTime: String
        var partyId: Int

        if (cursor!!.moveToFirst()){
            while (!cursor.isAfterLast){
                carMake= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getCarMake()))
                partyId= cursor.getInt(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getPartyId()))
                carModel= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getCarModel()))
                lisencePlate= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getLicensePlate()))
                firstName= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getFirstName()))
                lastName= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getLastName()))
                ssn= cursor.getLong(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getSsn()))
                phoneNum= cursor.getLong(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getPhone()))
                homeAddress= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getAddress()))
                state= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getState()))
                city= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getCity()))
                dateTime= cursor.getString(cursor.getColumnIndex(DBContract.PartiesInvolvedEntry.getDateTime()))
                var p1 = PartiesInvolved(dateTime, carMake, carModel, lisencePlate, firstName, lastName,
                    ssn, phoneNum, homeAddress, state, city)
                p1.setPartyId(partyId)
                partiesList.add(p1)

                cursor.moveToNext()
            }
        }
        return partiesList
    }

    /**
     * Delete specified report entry
     * @return [Boolean]
     */
    fun deleteReportEntry(reportId: Int): Boolean{
        val db = writableDatabase
        try {
            db.execSQL("DELETE FROM " + DBContract.ReportEntry.getTableReport() + " WHERE " +
            DBContract.ReportEntry.getColReportId() + "= '" + reportId + "'")
            db.close()
        } catch (e : SQLiteException) {

            return false
        }
        return true
    }

    /**
     * Delete specified partyInvolved entry
     * @return [Boolean]
     */
    fun deletePartyInvolved(partyId : Int): Boolean{
        val db = writableDatabase
        try{
            db.execSQL("DELETE FROM " + DBContract.PartiesInvolvedEntry.getTablePartiesInvolved() +
            " WHERE " + DBContract.PartiesInvolvedEntry.getPartyId()+ "= '" + partyId + "'")
            db.close()
        } catch(e : SQLiteException){

            return false
        }

        return true
    }

    /**
     * Delete specified Image entry
     * @return [Boolean]
     */
    fun deleteImage(imageId: Int): Boolean {
        // TODO: Double check Caleb's implementation of this here
        val db = writableDatabase
        try {
            db.execSQL("DELETE FROM " + DBContract.ImageEntry.getTableImages() +
                    " WHERE " + DBContract.ImageEntry.getImgId()+ "= '" + imageId + "'")
            db.close()
        } catch (e :SQLiteException) {

            return false
        }
        return true
    }

    /**
     * Creates all [SQLiteDatabase] tables
     */
    fun createAllTables(db: SQLiteDatabase){
        try {db.execSQL(SQL_CREATE_REPORT)} catch (e: SQLiteException) { Log.e("Report Table Error:", e.toString())}
        try {db.execSQL(SQL_CREATE_PARTIESINVOLVED)} catch (e: SQLiteException) { Log.e("PartiesInvolved Table Error:", e.toString())}
        try {db.execSQL(SQL_CREATE_WEATHER)} catch (e: SQLiteException) { Log.e("Weather Table Error:", e.toString())}
        try {db.execSQL(SQL_CREATE_IMAGES)} catch (e: SQLiteException) { Log.e("Images Table Error:", e.toString())}
    }

    /**
     * Drops all [SQLiteDatabase] tables
     */
    fun dropTables(db: SQLiteDatabase){
        db.execSQL(SQL_DELETE_WEATHER)
        db.execSQL(SQL_DELETE_PARTIES)
        db.execSQL(SQL_DELETE_REPORT)
        db.execSQL(SQL_DELETE_IMAGES)
    }

    /**
     * Companion object holds table data for [SQLiteDatabase]
     */
    companion object {
        val Database_version = 4
        val databaseName = "QUICKIT-DB"
        var test = DBContract.ReportEntry.getTableReport()
        //Log.d("insert a report: ", test)
        private var SQL_CREATE_REPORT =
            "Create table " + DBContract.ReportEntry.getTableReport() + " (" +
                    DBContract.ReportEntry.getColReportId() + " INTEGER PRIMARY KEY, " +
                    DBContract.ReportEntry.getColDate() + " TEXT NOT NULL UNIQUE," +
                    DBContract.ReportEntry.getColTitle() + " TEXT NOT NULL, " +
                    DBContract.ReportEntry.getColUsername() + " TEXT NOT NULL, " +
                    DBContract.ReportEntry.getColSummary() + " TEXT)"
        private var SQL_CREATE_PARTIESINVOLVED =
            "Create table " + DBContract.PartiesInvolvedEntry.getTablePartiesInvolved() + " (" +
                    DBContract.PartiesInvolvedEntry.getPartyId() + " INTEGER PRIMARY KEY, " +
                    DBContract.PartiesInvolvedEntry.getCarMake() + " TEXT," +
                    DBContract.PartiesInvolvedEntry.getCarModel() + " TEXT," +
                    DBContract.PartiesInvolvedEntry.getLicensePlate() + " TEXT," +
                    DBContract.PartiesInvolvedEntry.getFirstName() + " TEXT," +
                    DBContract.PartiesInvolvedEntry.getLastName() + " TEXT," +
                    DBContract.PartiesInvolvedEntry.getSsn() + " INTEGER," +
                    DBContract.PartiesInvolvedEntry.getPhone() + " INTEGER," +
                    DBContract.PartiesInvolvedEntry.getAddress() + " TEXT," +
                    DBContract.PartiesInvolvedEntry.getState()+ " TEXT," +
                    DBContract.PartiesInvolvedEntry.getCity() + " TEXT," +
                    DBContract.PartiesInvolvedEntry.getDateTime() + " TEXT NOT NULL," +
                    " FOREIGN KEY" + "(" + DBContract.PartiesInvolvedEntry.getDateTime() + ")" +
                    " REFERENCES " + DBContract.ReportEntry.getTableReport() + "(" +
                    DBContract.ReportEntry.getColDate() + ") ON UPDATE CASCADE ON DELETE CASCADE)"

        private var SQL_CREATE_WEATHER =
            "Create table " + DBContract.WeatherEntry.getTableWeather() + " (" +
                    DBContract.WeatherEntry.getWeatherId() + " INTEGER PRIMARY KEY, " +
                    DBContract.WeatherEntry.getCondition() + " TEXT," +
                    DBContract.WeatherEntry.getLocation() + " TEXT," +
                    DBContract.WeatherEntry.getWindSpeed() + " TEXT," +
                    DBContract.WeatherEntry.getTemp() + " TEXT," +
                    DBContract.WeatherEntry.getWindDir() + " TEXT," +
                    DBContract.WeatherEntry.getDateTime() + " TEXT NOT NULL," +
                    " FOREIGN KEY" + "(" + DBContract.WeatherEntry.getDateTime() + ")" +
                    " REFERENCES " + DBContract.ReportEntry.getTableReport() + "(" +
                    DBContract.ReportEntry.getColDate() + ") ON UPDATE CASCADE ON DELETE CASCADE)"

        private var SQL_CREATE_IMAGES =
            "Create table " + DBContract.ImageEntry.getTableImages() + " (" +
                    DBContract.ImageEntry.getImgId() + " INTEGER PRIMARY KEY, " +
                    DBContract.ImageEntry.getImgSrc() + " TEXT," +
                    DBContract.ImageEntry.getDateTime() + " TEXT NOT NULL," +
                    " FOREIGN KEY" + "(" + DBContract.ImageEntry.getDateTime() + ")" +
                    " REFERENCES " + DBContract.ReportEntry.getTableReport() + "(" +
                    DBContract.ReportEntry.getColDate() + ") ON UPDATE CASCADE ON DELETE CASCADE)"

        private val SQL_DELETE_PARTIES = "Drop table if exists " + DBContract.PartiesInvolvedEntry.getTablePartiesInvolved()
        private val SQL_DELETE_REPORT = "Drop table if exists " + DBContract.ReportEntry.getTableReport()
        private val SQL_DELETE_WEATHER = "Drop table if exists " + DBContract.WeatherEntry.getTableWeather()
        private val SQL_DELETE_IMAGES = "Drop table if exists " + DBContract.ImageEntry.getTableImages()

    }


}