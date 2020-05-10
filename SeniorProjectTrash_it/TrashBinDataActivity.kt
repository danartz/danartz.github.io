package com.example.trash_it

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.trash_bin_data_activity_view.*
import kotlinx.android.synthetic.main.trash_view.*

class TrashBinDataActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        var deleteSignalFlag = false
        super.onCreate(savedInstanceState)
        var context = this.applicationContext
        val daysOfWeek: Array<String> = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        setContentView(R.layout.trash_bin_data_activity_view)
        val trashBinHistoryListView = findViewById<ListView>(R.id.trashBinHistoryList)
        var trashDataList = ArrayList<TrashData>()
        var position: Int
        position = -1
        val dummyView = findViewById<ListView>(R.id.dummyListView)
        dummyView.visibility = View.INVISIBLE

        //send list minus first item and then first item separately
        if(this.getIntent().hasExtra("trashDataList")) {
            trashDataList = (this.getIntent().getParcelableArrayListExtra("trashDataList"))
            if(trashDataList[0].alphaNum != null) {
                var binName : TextView
                binName = findViewById<TextView>(R.id.binName)
                binName.text = trashDataList[0].name
                var lastEmptied: TextView
                lastEmptied = findViewById<TextView>(R.id.lastEmptiedField)
                var heightCapacity: TextView
                heightCapacity = findViewById<TextView>(R.id.heightCapacity)
                var trashDayPrediction : TextView
                trashDayPrediction = findViewById<TextView>(R.id.trashDayPred)

                setLabels(trashDataList, trashDayPrediction, heightCapacity, weightCapacity, lastEmptied,
                    daysOfWeek)

                // get index for trash bin to allow for autoupdating of history items
                if(this.getIntent().hasExtra("positionIndex")){
                    position = this.getIntent().getIntExtra("positionIndex", -1)
                }else{
                    position = -1
                }

                var trashControl = TrashController()
                var ref = trashControl.getTrashReference()

                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if(!deleteSignalFlag && position >= 0){
                            trashDataList = arrayListOf()
                            trashControl.populateTrashList(p0, dummyView, context, "0")
                            trashDataList = trashControl.buildTrashDataList(position)

                            trashBinHistoryListView.adapter = TrashBinHistoryAdapter(context, trashDataList.size, trashDataList)
                            setLabels(trashDataList, trashDayPrediction, heightCapacity, weightCapacity, lastEmptied,
                                daysOfWeek)
                        }

                        // Toast.makeText(context, position.toString(), Toast.LENGTH_LONG).show()

                    }


                })

            }

            Log.d("trash sent new list: ", trashDataList.size.toString())
            trashBinHistoryListView.adapter = TrashBinHistoryAdapter(this.applicationContext, trashDataList.size, trashDataList)


        }else{
            Log.d("ERROR: no package sent ", "error")
        }





        clearBin.setOnClickListener{
            var trashControl = TrashController()
            if(this.getIntent().hasExtra("trashDataList")){
                trashControl.emptyTrashBin(trashDataList[0].alphaNum)
                //change to home screen
                deleteSignalFlag = true
                trashDataList = ArrayList()
                trashBinHistoryListView.adapter = TrashBinHistoryAdapter(this.application, trashDataList.size, trashDataList)


            }
        }

        trash_home_icon.setOnClickListener {
            val intent = Intent(this, MainActivity :: class.java)

            startActivity(intent)
            overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)
        }



    }

    fun checkDayOfWeek(days: Array<String>, lastEmptiedDay: String): Int {
        var count = 0

        for (day in days){
            if(day.equals(lastEmptiedDay)){
                return count
            }
            count++
        }
        return -1
    }

    fun setLabels(trashDataList: ArrayList<TrashData>, trashDayPrediction: TextView, heightCapacity: TextView,
                  weightCapacity: TextView, lastEmptied: TextView, daysOfWeek: Array<String>){

        var lastDayEmptiedDay = trashDataList[0].lastEmptied
        lastEmptied.text = "NULL"
        if(!trashDataList[0].lastEmptied.equals("")) {
            var lastEmptiedSplit = trashDataList[0].lastEmptied.split(" ")
            lastEmptied.text = lastEmptiedSplit[1] + " " + lastEmptiedSplit[2]

            lastDayEmptiedDay = trashDataList[0].lastEmptied.substringBefore(" ")
            // function call to check day of week
        }
        var dayOfWeekPredIndex = checkDayOfWeek(daysOfWeek, lastDayEmptiedDay)

        // set text for day of week prediction to a default value of the last day emptied + 2
        if(dayOfWeekPredIndex != -1){
            // circular array
            var heightCapacity = calculateHeightCap(trashDataList)
            if(heightCapacity <= 24) {
                //Toast.makeText(this, daysOfWeek[dayOfWeekPredIndex], Toast.LENGTH_LONG).show()
                trashDayPrediction.text = daysOfWeek[(dayOfWeekPredIndex + 3) % daysOfWeek.size]
            }
            else if((heightCapacity >= 25) && (heightCapacity <= 49))
            {
                trashDayPrediction.text = daysOfWeek[(dayOfWeekPredIndex + 2) % daysOfWeek.size]
            }
            else if((heightCapacity >= 50) && (heightCapacity <= 74)){
                trashDayPrediction.text = daysOfWeek[(dayOfWeekPredIndex + 1) % daysOfWeek.size]
            }else{
                trashDayPrediction.text = daysOfWeek[dayOfWeekPredIndex]
            }

        }else{
            trashDayPrediction.text = "Mon"
        }

        if(trashDataList[0].maxHeight != 0){
            var heightCapacityInt = calculateHeightCap(trashDataList)
            heightCapacity.text = heightCapacityInt.toString() + "%"
        }
        else
            heightCapacity.text = "0%"
        var weightCapacity: TextView
        weightCapacity = findViewById<TextView>(R.id.weightCapacity)
        if(trashDataList[0].maxWeight !=0) {
            var weightCapacityInt = calculateWeightCap(trashDataList)
            weightCapacity.text = weightCapacityInt.toString() + "%"
        }
        else
            weightCapacity.text = "0%"


    }

    fun calculateHeightCap(trashDataList: ArrayList<TrashData>): Int{
        return (100.0*(trashDataList[0].height.toDouble() / trashDataList[0].maxHeight.toDouble())).toInt()
    }

    fun calculateWeightCap(trashDataList: ArrayList<TrashData>): Int{
        return (100.0*(trashDataList[0].weight.toDouble() / trashDataList[0].maxWeight.toDouble())).toInt()
    }

}