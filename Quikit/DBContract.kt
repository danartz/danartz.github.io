package com.example.quickreport

import android.icu.text.StringPrepParseException
import android.provider.BaseColumns

/**
 * DBContract simplifies the column selection of a [DatabaseManager] table
 * Stored fields are names of tables and columns
 */

object DBContract {
    /* Inner class that defines the table contents */

    /**
     * Allows for easy selection of the table Report
     */
    class ReportEntry : BaseColumns {
        companion object {

            private val tableReport = "Report"
            private val col_reportId = "id"
            private val col_date = "date"
            private val col_summary = "summary"
            private val col_partyId = "partyId"
            private val col_username = "username"
            private val col_title = "title"

            fun getTableReport(): String {
                return this.tableReport
            }

            fun getColUsername(): String{
                return this.col_username
            }

            fun getColReportId(): String {
                return this.col_reportId
            }

            fun getColTitle(): String{
                return this.col_title
            }

            fun getColDate(): String {
                return this.col_date
            }

            fun getColSummary(): String {
                return col_summary
            }

            fun getPartyId(): String {
                return col_partyId
            }
        }
    }

    /**
     * Allows for easy selection of the table PartiesInvolved
     */
    class PartiesInvolvedEntry : BaseColumns {
        companion object {
            private val tablePartiesInvolved = "PartiesInvolved"
            private val col_partyId = "partyId"
            private val col_carMake = "carMake"
            private val col_carModel = "carModel"
            private val col_licensePlate = "licensePlate"
            private val col_firstName = "firstName"
            private val col_lastName = "lastName"
            private val col_ssn = "ssn"
            private val col_phoneNum = "phoneNum"
            private val col_homeAddress = "homeAddress"
            private val col_state = "state"
            private val col_city = "city"
            private val col_dateTime = "dateTime"

            fun getTablePartiesInvolved(): String {
                return this.tablePartiesInvolved
            }

            fun getCarMake(): String {
                return this.col_carMake
            }

            fun getCarModel(): String {
                return this.col_carModel
            }

            fun getLicensePlate(): String {
                return this.col_licensePlate
            }

            fun getFirstName(): String {
                return this.col_firstName
            }

            fun getLastName(): String {
                return this.col_lastName
            }

            fun getSsn(): String {
                return this.col_ssn
            }

            fun getPhone(): String {
                return this.col_phoneNum
            }

            fun getAddress(): String {
                return this.col_homeAddress
            }

            fun getState(): String {
                return this.col_state
            }

            fun getCity(): String {
                return this.col_city
            }

            fun getPartyId(): String {
                return this.col_partyId
            }

            fun getDateTime(): String {
                return this.col_dateTime
            }

        }
    }

    /**
     * Allows for easy selection of Weather Table
     */

    class WeatherEntry : BaseColumns {
        companion object {
            private val tableWeather = "Weather"
            private val col_weatherId = "weatherId"
            private val col_location = "location"
            private val col_condition = "condition"
            private val col_windSpeed = "windSpeed"
            private val col_windDir = "windDir"
            private val col_dateTime = "dateTime"
            private val col_temp = "temp"

            fun getTableWeather(): String {
                return this.tableWeather
            }

            fun getLocation(): String {
                return this.col_location
            }

            fun getCondition(): String {
                return this.col_condition
            }

            fun getWindSpeed(): String {
                return this.col_windSpeed
            }

            fun getWindDir(): String {
                return this.col_windDir
            }

            fun getWeatherId(): String {
                return this.col_weatherId
            }

            fun getDateTime(): String {
                return this.col_dateTime
            }
            fun getTemp(): String{
                return this.col_temp
            }
        }
    }

    /**
     * Allows for easy selection of Images table
     */
    class ImageEntry : BaseColumns {
        companion object {
            private val tableImages = "Images"
            private val col_imgId = "imgId"
            private val col_imgSrc = "imgSrc"
            private val col_dateTime = "dateTime"

            fun getTableImages(): String {
                return this.tableImages
            }

            fun getImgId(): String {
                return this.col_imgId
            }

            fun getImgSrc(): String {
                return this.col_imgSrc
            }

            fun getDateTime(): String {
                return this.col_dateTime
            }
        }
    }

    /**
     * This ArrayList holds the partiesInvolved list that is shared and passed between
     * AddPartiesInvolved and ViewEditReport Screen
     * Position keeps track of selected party invovled. If position == -1 then the selection doesn't exist
     */
    class PartiesInvolvedList {
        companion object {
            private val name_array = ArrayList<PartiesInvolved>()
            private var dateTime = ""
            private var position = -1

            fun getPartiesArray(): ArrayList<PartiesInvolved> {return this.name_array}
            fun getDateTime(): String{return this.dateTime}
            fun setPosition(pos: Int){this.position = pos}
            fun getPosition(): Int{return this.position}
            fun setDefaultPosition(){this.position = -1}

            fun reset_partiesInvolvedList() {
                name_array.clear()
            }
            fun reset_Datetime(){
                dateTime = ""
            }
            fun setDateTime(date: String){this.dateTime = date}

        }
    }

    /**
     *
     */

    /**
     * Stores temporary report information for screen changes
     */

    class ReportInfo{
        companion object{
            private var summary: String? = null
            private var username: String? = null
            private var dateTime: String? = null
            private var title: String? = null
            private var reportId: Int = -1

            private var condition: String? = null
            private var location: String? = null
            private var windSpeed: String? = null
            private var windDir: String? = null
            private var temp: String? = null

            fun getCondition():String?{return condition}
            fun getLocation():String?{return location}
            fun getWindSpeed():String?{return windSpeed}
            fun getWindDir():String?{return windDir}
            fun getTemp(): String?{return temp}

            fun getSummary(): String?{return this.summary}
            fun getUsername(): String?{return this.username}
            fun getDateTime(): String?{return this.dateTime}
            fun getTitle(): String?{return this.title}
            fun getReportId(): Int{return this.reportId}
            fun setReportId(reportIdField: Int){this.reportId = reportIdField}
            fun isNull(): Boolean{
                if (summary == null && username == null && title == null)
                    return true
                return false
            }
            fun isWeatherNull(): Boolean{
                if(condition == null && location == null && windSpeed == null && windDir == null){
                    return true
                }
                return false
            }

            fun setReportInfo(summaryField: String?, usernameField: String?, dateTimeField: String?,
                              titleField: String?){
                this.summary = summaryField
                this.username = usernameField
                this.dateTime = dateTimeField
                this.title = titleField
            }

            fun setWeather(condition: String?, location: String?, windSpeed: String?, windDir: String?,
                           temp: String?){
                this.condition = condition
                this.location = location
                this.windSpeed = windSpeed
                this.windDir = windDir
                this.temp = temp
            }

            fun resetReportInfo(){
                summary = null
                username = null
                dateTime = null
                title = null
                reportId = -1
                condition = null
                location = null
                windSpeed = null
                windDir = null
            }
            fun setSummary(summaryField: String?){this.summary = summaryField}
            fun setUserName(usernameField: String?){this.username = usernameField}
            fun setDateTime(dateTimeField: String?){this.dateTime = dateTimeField}
            fun setTitle(titleField: String?){this.title = titleField}
        }
    }
}