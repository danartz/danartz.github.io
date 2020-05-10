package com.example.trash_it

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    var binNearFull: String = ""
    lateinit var alphaNumList: MutableList<String>



    override fun onCreate(savedInstanceState: Bundle?) {
        var context = this.applicationContext
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.trashListView)
        val trashBinHistoryListView = findViewById<ListView>(R.id.trashBinHistoryList)

        var trashControl = TrashController()

        var ref = trashControl.getTrashReference()

        // Check if user is connected to Firebase
        val connectedRef =
            FirebaseDatabase.getInstance().getReference(".info/connected")
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected =
                    snapshot.getValue(Boolean::class.java)!!
                if (connected) {
                    var offlineMessage = findViewById<TextView>(R.id.connectionStatus)
                    offlineMessage.visibility = View.INVISIBLE

                    var listViewOnline = findViewById<ListView>(R.id.trashListView)
                    listViewOnline.visibility = View.VISIBLE

                } else {

                    var offlineMessage = findViewById<TextView>(R.id.connectionStatus)
                    offlineMessage.setText("You Are Not Connected")
                    offlineMessage.visibility = View.VISIBLE

                    var listViewOnline = findViewById<ListView>(R.id.trashListView)
                    listViewOnline.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                System.err.println("Listener was cancelled")
            }
        })
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                // List is for test purposes for syncing

                if(alphaNumList.size == 0){
                    var dummyValue = ""
                    alphaNumList.add(dummyValue)
                }

                // pass list that gets loaded from file saved to local storage on startup of previously paired bins.
                trashControl.populateTrashList(p0, listView, context, "1")



            }


        })



        // Allows items to be clicked in listview
        listView.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                // get string of trashBinNumList and then search trashList for all items with the same binId
                // add those items to their own list and push that list to the bin screen
                var trashDataList = trashControl.buildTrashDataList(position)
                //Log.d("trash Main value check ", trashDataList[2].dateTimeField.toString())
                //Log.d("trash list size: ", trashDataList.size.toString())
                val intent = Intent(this@MainActivity, TrashBinDataActivity :: class.java)
                var bundle = Bundle()
                bundle.putParcelableArrayList("trashDataList", trashDataList)

                // Push index then rebuild trashDataList in the trashBinDataActivity screen with respect to that index
                // Use another addValueEventListener

                bundle.putInt("positionIndex", position)
                //intent.putParcelableArrayListExtra("trashDataList", trashDataList)
                intent.putExtras(bundle)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            }

        }


        // add user input to list of paired trash bins that will get passed
        pairButton.setOnClickListener {
            var editTextHello = findViewById(R.id.pairBinName) as EditText
            var trashBinAlphaNum = editTextHello.text.toString()
            editTextHello.setText("")
            alphaNumList.add(trashBinAlphaNum)
            var ref = trashControl.getTrashReference()
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    // List is for test purposes for syncing

                    if(alphaNumList.size == 0){
                        var dummyValue = ""
                        alphaNumList.add(dummyValue)
                    }
                    trashControl.populateTrashList(p0, listView, context, trashBinAlphaNum)
                }

            })

        }

        /*insertBin.setOnClickListener {
            var trashControl = TrashController()
            trashControl.testInsertData(context, 95, 6, "Mon Mar 13 15:02:23 2020",
              "test8", "Tue Mar 12 15:02:23 2020", "bin-8", 100, 100)

        }*/



    }

    init{
        alphaNumList = mutableListOf()
    }
}
