package com.example.quickreport

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.reportlistview.appTitle
import kotlinx.android.synthetic.main.view_edit_report.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ViewEditReport : AppCompatActivity(){

    var reportDate = getCurrentDateTime()
    lateinit var databaseManager : DatabaseManager
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1
    var image_uri: Uri? = null
    var summary: String? = null
    var reportTitle: String? = null
    var username: String? = null

    private var creatingNew: Boolean = false
    private lateinit var report: Report

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_edit_report)
        databaseManager = DatabaseManager(this)
        val context = this

        // Determine if we're creating a new Report, and load the existing one if not
        this.creatingNew = intent.getBooleanExtra("CreatingNew", true)
        if (!this.creatingNew)
            this.report = intent.getSerializableExtra("Report") as Report
        else
            this.report = Report()

        val partiesStringList = ArrayList<String?>()
        if(!this.creatingNew){
            if (this.report.getWeather().getLocation() != "NO_CITY, NO_STATE"){
                // get weather should only be visible when creating a new report
                location.visibility = View.INVISIBLE
                edittext_location.visibility = View.INVISIBLE
                getWeather.visibility = View.INVISIBLE
                // display weather report since it already exists
                setWeatherFields()
            }

            Log.d("report size: ", this.report.getPartiesInvolved().size.toString())
            show_date.setText(this.report.getDate())
            reportDate = this.report.getDate()
            setFields()
        }

        //Set ArrayList of first name + last name of victims to display on report screen
        for (party in this.report.getPartiesInvolved()){
            partiesStringList.add(party.getLastName() +
            ", " + party.getFirstName())
        }

        // Click to go to home screen and lose changes to current report
        this.appTitle.setOnClickListener {
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }

        //Parties Involved ListView
        val adapter = ArrayAdapter(this, R.layout.parties_involved_list_item, partiesStringList)
        val listView:ListView = findViewById(R.id.partiesInvolvedListView)
        listView.setAdapter(adapter)
        //End ListView
        //Add onItemClickListener https://www.tutorialkart.com/kotlin-android/kotlin-android-listview-example/

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View,
                                     position: Int, id: Long) {

                // value of item that is clicked
//                val itemValue = listView.getItemAtPosition(position) as String
                val itemValue2 = this@ViewEditReport.report.getPartiesInvolved()[position].toString()
                //TODO: Go back to the Add Victim screen with the details populated
                // Allow users to edit/delete the details, then save new victim in arraylist
                // Toast the values
                Toast.makeText(applicationContext,
                    "Position :$position\nItem Value : $itemValue2", Toast.LENGTH_LONG)
                    .show()

                val intent = Intent(this@ViewEditReport, AddPartiesInvolved ::class.java)
                intent.putExtra("CreatingNew", false)
                intent.putExtra("Report", this@ViewEditReport.report)
                intent.putExtra("Index", position)
                startActivity(intent)
            }
        }


        addVictimButton.setOnClickListener {
            setReportValues()
            Log.d("state test", "saved report details")
            val intent = Intent(this, AddPartiesInvolved ::class.java)
            intent.putExtra("CreatingNew", true)
            intent.putExtra("Report", this.report)
            startActivity(intent)
        }

        getWeather.setOnClickListener {
            var location = edittext_location.text.toString()
            var locationCheck: Int? = location.toIntOrNull()
            // Validate if the input is an integer
            if(locationCheck != null) {
                this.report.setWeather(Weather("", "", location))
                var weather = this.report.getWeather()
                if (!weather.collectWeather().contains(false)) {
                    condition.text = weather.getCondition()
                    locationField.text = weather.getLocation()
                    windSpeed.text = weather.getWind()
                    windDir.text = weather.getWindDir()
                    temp.text = weather.getTemp()
                } else {
                    condition.text = "Error collecting weather."
                }
            }else{
                Toast.makeText(applicationContext,
                    "Must supply a valid zip code", Toast.LENGTH_LONG)
                    .show()
            }
        }

        // Insert report
       SubmitButton.setOnClickListener {
           // Redundant logic left in to retain runtime information functionality such as Log.d(...)
           // If new report
           if(this.creatingNew) {
               setReportValues()
               setWeatherFields()
               report.saveReport(this)
               // Redundant calls to be removed later
               Log.d("testSummary", this.report.getSummary())
               Log.d("insert a report: ", this.report.toString() + " Submitted Successfully")
           }else{
               setReportValues()
               setWeatherFields()
               report.saveReport(this)
               val db = DatabaseManager(context)
               //db.updateReport(this.report)

           }
           this.creatingNew = false
           val intent = Intent(this, ReportListView ::class.java)
           startActivity(intent)
       }


        // Testing button, no relevant features for end User
        // Only remaining violation of Design Contract (accessing DB directly)
        //Query test
      /*  queryEntry.setOnClickListener {
            var db = DatabaseManager(context)

            var c1 = db.queryAllSummary()
            for (i in 0..c1.size - 1)
                Log.d("db test", c1[i].getReportUser().toString())
            var c2 = db.queryParties(reportDate)
            for (i in 0..c2.size - 1)
               Log.d("db test", c2[i].getCarMake())
            Log.d("State test", this.creatingNew.toString())

        }*/

        //Cancel Button Listener
        //If new report cancel goes to home screen, otherwise to report list screen
        cancelButton.setOnClickListener {
            if(this.creatingNew) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, ReportListView::class.java)
                startActivity(intent)
            }
        }

        // Image List View
        viewPhotosButton.setOnClickListener {
            setReportValues()
            val intent = Intent(this, ImageListView ::class.java)
            intent.putExtra("Report", this.report)
            startActivity(intent)
        }

        /**
         * TAKE A PHOTO
         */
        takePicButton.setOnClickListener {
            //if system os is Marshmallow or Above, we need to request runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    //permission was not enabled
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    openCamera()
                }
            }
            else{
                //system os is < marshmallow
                openCamera()
            }
        }
    }

//    private fun openCamera() {
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, "New Picture")
//        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
//        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//        //camera intent
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
//        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
//    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent,  IMAGE_CAPTURE_CODE)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    openCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK){
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            report.getImageList().add(ImgWrapper(imageBitmap, this.getCurrentDateTime()))
        }
    }


    private fun getCurrentDateTime(): String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:MM:SS.SSS")

        return current.format(formatter)
    }

    private fun setReportValues(){
        report.setSumary(edittext_summary.text.toString())
        report.setUsername(edittext_username.text.toString())
        report.setTitle(edittext_reportTitle.text.toString())
        // Added logic to prevent overwrite of old date
        if (creatingNew)
            report.setDate(reportDate)
    }

    private fun setFields(){
        edittext_summary.setText(this.report.getSummary())
        edittext_username.setText(this.report.getUsername())
        edittext_reportTitle.setText(this.report.getTitle())
    }

    fun assignToParty(i: Int): PartiesInvolved{
        var party: PartiesInvolved = this.report.getPartiesInvolved()[i]
        Log.d("DateTime of new party", party.getDateTime().toString())
        return party
    }

    fun createParty(i: Int): PartiesInvolved{
        return this.report.getPartiesInvolved()[i]
    }

    private fun setWeatherFields(){
        var weather: Weather = this.report.getWeather()
        condition.text = weather.getCondition()
        locationField.text = weather.getLocation()
        windSpeed.text = weather.getWind()
        windDir.text = weather.getWindDir()
        temp.text = weather.getTemp()
    }




}