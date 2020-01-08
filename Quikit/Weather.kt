package com.example.quickreport
import android.os.AsyncTask
import android.util.Log
import android.util.TimeUtils
import org.json.JSONObject
import java.io.Serializable
import java.lang.Exception
import java.lang.NullPointerException
import java.net.URL
import java.time.LocalDateTime
import kotlin.math.abs

/**
 * Weather class holds basic weather information for a specified location
 * When creating an instance...
 * give either a city and state argument
 * OR
 * give a zip code argument

 * The latter is the preferred method as it would be more accurate
 */
class Weather(city: String = "", state: String = "", zip: String = "NO_ZIP") : Serializable {
    val apiKey: String = "0a77dd0a803a3765fa6cb6f329eea5fb"
    private var weatherId = -1
    private var location: String = ""
    private var temp: String = ""
    private var condition: String = ""
    private var wind: String = ""
    private var windDir: String = ""
    private var dateTime: String = ""
    private var response: String = ""
    private var dataRetrieved: Boolean = false

    fun getLocation(): String {
        return this.location
    }

    fun getCondition(): String {
        return this.condition
    }

    fun getWind(): String {
        return this.wind
    }

    fun getWindDir(): String {
        return this.windDir
    }

    fun getDateTime(): String {
        return this.dateTime
    }

    fun getWeatherId(): Int {
        return this.weatherId
    }
    fun getTemp(): String{return this.temp}

    fun setDateTime(datetimeField: String){this.dateTime = datetimeField}
    fun setCondition(conditionField: String){this.condition = conditionField}
    fun setWind(windField: String){this.wind = windField}
    fun setWindDir(windDirField: String){this.windDir = windDirField}
    fun setLocation(locationField: String){this.location = locationField}
    fun setWeatherID(weatherId: Int) { this.weatherId = weatherId}
    fun setTemp(tempField: String){this.temp = tempField}

    init {
        // Given a city, State location
        if (zip == "NO_ZIP") {
            this.location = "$city, $state"
        }

        // Given a zip location
        else {
            this.location = zip
        }
    }

    inner class weatherTask() : AsyncTask<Void, Void, Void?>() {

        override fun doInBackground(vararg params: Void?): Void? {
            var response:String
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$location&units=metric&appid=$apiKey").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = "NO_RESPONSE"
                println("Couldn't collect from API.")
                println(e)
            }

            this@Weather.response = response
            this@Weather.dataRetrieved = true

            return null
        }
    }

    /**
     * Collects the weather data and returns a Boolean array representing success results
     * @returns true for success, false for error
     * @returns [[temp], [cond], [wind], [windDir]]
     */
    fun collectWeather(): Array<Boolean> {
        // Todo: Fix the dumb way I made of assinging values where if 1 isn't present, the rest fail... Dummy
        this.dataRetrieved = false
        weatherTask().execute()

        val tick = LocalDateTime.now().second
        while (!this.dataRetrieved && abs(LocalDateTime.now().second - tick) < 3) { }

        var toReturn: Array<Boolean> = Array(4) {false}

        // Weather wasn't retrieved successfully, leave values at null
        if (response == "NO_RESPONSE") {
            return toReturn
        }

        // Parse the data received into a JSON Object
        try{
            val weatherObj: JSONObject? = JSONObject(response)
            val conditions: JSONObject? = weatherObj?.getJSONArray("weather")?.getJSONObject(0)
            val tempObj: JSONObject? = weatherObj?.getJSONObject("main")
            val windObj: JSONObject? = weatherObj?.getJSONObject("wind")

            this.temp = tempObj!!.getString("temp")
            toReturn[0] = (temp != "")
            this.condition = conditions!!.getString("description")
            toReturn[1] = (condition != "")
            this.wind = windObj!!.getString("speed")
            toReturn[2] = (wind != "")
            this.windDir = windObj.getString("deg")
            toReturn[3] = (windDir != "")
        }
        catch (e: NullPointerException) {
            println("Unable to assign all values, Null Ptr")
            println(e)
        }
        catch (e: Exception) {
            Log.e("Weather JSON Parse error:", e.toString())
        }

        return toReturn
    }

}