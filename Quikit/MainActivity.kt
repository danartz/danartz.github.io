package com.example.quickreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var databaseManager : DatabaseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonReportList.setOnClickListener {
            val intent = Intent(this, ReportListView ::class.java)
            startActivity(intent)
        }

        createReport.setOnClickListener {
            val intent = Intent(this, ViewEditReport ::class.java)
            intent.putExtra("CreatingNew", true)
            startActivity(intent)
        }
    }

}

