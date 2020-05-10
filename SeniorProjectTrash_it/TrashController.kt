package com.example.trash_it

import android.content.Context
import android.content.Intent
import java.lang.Integer.parseInt
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.io.File
import java.io.InputStream
import java.lang.NumberFormatException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TrashController() {

    lateinit var trashList: MutableList<TrashData>
    lateinit var trashListReverse: MutableList<TrashData>
    lateinit var trashBinNumList: MutableList<String>
    // make list of most recent item in each trash bin
    lateinit var trashBinRecentEntry: MutableList<TrashData>

    fun testInsertData(
        context: Context,
        weight: Int,
        height: Int,
        dateTime: String,
        alphaNum: String,
        lastEmptied: String,
        name: String,
        maxHeight: Int,
        maxWeight: Int
    ) {
        var trash =
            TrashData(weight, height, dateTime, alphaNum, lastEmptied, name, maxHeight, maxWeight)
        saveTrashData(trash, context)
    }

    //modify to get reference of alphanumeric
    fun getTrashReference(): DatabaseReference{
        return FirebaseDatabase.getInstance().getReference()
    }

    //modify to get reference of alphanumeric
    fun getTrashQuery(): Query{
        var query = getTrashReference()
        return query.orderByChild("weight")
    }

    fun populateTrashList(
        p0: DataSnapshot,
        listView: ListView,
        context: Context,
        alphaNumKey: String
    ) {
        trashList.clear()
        trashBinNumList.clear()
        trashListReverse.clear()
        trashBinRecentEntry.clear()
        Log.d("trash bin num size", trashBinNumList.size.toString())
        var fileData: MutableList<String>
        // Have to send empty adapter from trash binDataActivity, this checks which screen is populating the trash list
        var trashBinAdapterflag = false
        if(alphaNumKey.equals("1"))
            trashBinAdapterflag = true
        //Add file to list and then add new bin to end of list if it isn't already saved
        fileData = mutableListOf()
        fileData.addAll(readFile(context))
        //Check if item is in list
        if (!fileData.find { it.contains(alphaNumKey) }.equals(alphaNumKey) && !alphaNumKey.equals("")) {
            fileData.add(alphaNumKey)
            Log.d("fileData size: ", fileData.size.toString())
        }
        Log.d("fileData size before: ", fileData.size.toString())
        if (p0!!.exists()) {
            for (entry in fileData) {
                var h = p0.child(entry)
                var trash = TrashData()
                val trashData = h.children
                Log.d("trashData value: ", trashData.toString())

                if (h.key == entry) {
                    for (p in trashData) {
                        val trashEntries = p.children
                        for (i in trashEntries) {
                            trash = TrashData()
                            trash.alphaNum = i.child("alphaNum").getValue().toString()
                            trash.lastEmptied = i.child("lastEmptied").getValue().toString()
                            trash.name = i.child("name").getValue().toString()
                            Log.d("lastEmptied", i.child("lastEmptied").getValue().toString())

                            trash.dateTime = i.child("dateTime").getValue().toString()
                            if(checkEntry(i.child("height").getValue().toString())){
                                trash.height =
                                    Integer.valueOf(i.child("height").getValue().toString())
                            }else{
                                trash.height = 0
                            }
                            if(checkEntry(i.child("weight").getValue().toString())){
                                trash.weight =
                                    Integer.valueOf(i.child("weight").getValue().toString())
                            }else{
                                trash.weight= 0
                            }
                            if(checkEntry(i.child("maxHeight").getValue().toString())){
                                trash.maxHeight =
                                    Integer.valueOf(i.child("maxHeight").getValue().toString())
                            }else{
                                trash.maxHeight = 1
                            }
                            if(checkEntry(i.child("maxWeight").getValue().toString())){
                                trash.maxWeight =
                                    Integer.valueOf(i.child("maxWeight").getValue().toString())
                            }else{
                                trash.maxWeight = 1
                            }
                            trashList.add(trash)
                        }

                    }

                    if(!trash.name.equals("")) {
                        trashBinNumList.add(trash.name)
                        trashBinRecentEntry.add(trash)
                    }

                }
            }
            if (!searchForBin(p0, alphaNumKey)) {
                if(alphaNumKey !="" && alphaNumKey !="1" && alphaNumKey !="0"){
                    Toast.makeText(
                        context,
                        "Error: $alphaNumKey is an invalid alpha-numeric key",
                        Toast.LENGTH_LONG
                    ).show()
                }

                // remove alphaNumKey from list if it isn't in database
                if (fileData.size > 0 && fileData[fileData.size - 1].equals(alphaNumKey))
                    fileData.removeAt(fileData.size - 1)
            }

        }
        saveToFile(fileData, context)
        Log.d("fileData size after: ", fileData.size.toString())
        reverseTrashList()

        if(trashBinAdapterflag){
            val adapter = TrashBinAdapter(context, R.layout.trash_view, trashBinNumList, trashBinRecentEntry)
            listView.adapter = adapter
        }

        //adapter.notifyDataSetChanged()

    }

    //Search for new paired item. Return true if it exists
    fun searchForBin(p0: DataSnapshot, binAlpha: String): Boolean {
        if (p0.exists()) {
            if(p0.hasChild(binAlpha))
                return true
        }
        return false
    }

    // Verify database entry is an integer
    fun checkEntry(value: String): Boolean{
        var numeric = true
        try {
            var entry = parseInt(value)
        }catch(e: NumberFormatException){
           numeric = false
        }
        if(numeric){
            return true
        }
        return false
    }

    // Deletes a trash bin and all its data.
    fun emptyTrashBin(binId: String) {
       getTrashReference().child(binId).removeValue()
    }

    //save alphanumeric key for trash bin in text file if it doesn't already exist in said file
    fun saveToFile(alphaKeyList: MutableList<String>, context: Context) {

        val fileName = context.filesDir.toString() + "data9.txt"
        var file = File(fileName)

        file.printWriter().use { out ->
            for (w in alphaKeyList) {
                out.println(w)
            }
        }

    }

    fun readFile(context: Context): MutableList<String> {
        var fileData: MutableList<String>
        fileData = mutableListOf()
        val fileName = context.filesDir.toString() + "data9.txt"
        var file = File(fileName)
        var inputStream: InputStream
        if (file.exists()) {
            inputStream = file.inputStream()
            inputStream.bufferedReader().useLines { lines -> lines.forEach { fileData.add(it) } }
            fileData.forEach { println(">  " + it) }
        }

        return fileData

    }

    // Change this to order by date
    fun reverseTrashList() {
        for (i in trashList.size - 1 downTo 0) {
            var trash = TrashData()
            trash.lastEmptied = trashList[i].lastEmptied
            trash.name = trashList[i].name
            trash.alphaNum = trashList[i].alphaNum
            trash.dateTime = trashList[i].dateTime
            trash.height = trashList[i].height
            trash.weight = trashList[i].weight
            trash.maxHeight = trashList[i].maxHeight
            trash.maxWeight = trashList[i].maxWeight
            trashListReverse.add(trash)
        }
    }

    fun saveTrashData(trashData: TrashData, context: Context) {
        val ref = FirebaseDatabase.getInstance().getReference(trashData.alphaNum).child(trashData.dateTime)
        //val refDate = ref.child(trashData.dateTime)
        var trashIdKey = ref.push().key
        if (trashIdKey != null) {
            ref.child(trashIdKey).setValue(trashData).addOnCompleteListener {
                Toast.makeText(context, "trash saved successfully", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "trash save failed", Toast.LENGTH_LONG).show()
        }

    }

    //If the bin id at the clicked location equals the binID of an item in the trashBinNumList, then it's added to a new list and returned
    fun buildTrashDataList(position: Int): ArrayList<TrashData> {
        var trashDataList: ArrayList<TrashData>
        trashDataList = ArrayList<TrashData>()
        Log.d("buildtrashSize: ", trashList.size.toString())
        for (i in 0..trashListReverse.size - 1) {
            if (trashBinNumList[position] == trashListReverse[i].name)
                trashDataList.add(trashListReverse[i])
        }
        return trashDataList
    }

    init {
        trashList = mutableListOf()
        trashBinNumList = mutableListOf()
        trashListReverse = mutableListOf()
        trashBinRecentEntry = mutableListOf()
    }


}