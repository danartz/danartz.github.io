package com.example.quickreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.reportlistview.*

class ReportListView : AppCompatActivity() {

    private lateinit var rs: ReportSummary
    private lateinit var reportSums: ArrayList<ReportSummary>
    private var reportIndex = 0 // The starting point of the currently displayed reports
    private var totalReports = 0

    private fun load5Reports(): Array<ReportSummary> {
        var toReturn: ArrayList<ReportSummary> = ArrayList(0)
        for (i in this.reportIndex until reportIndex + 5) {
            if (reportSums.size == i)
                break
            toReturn.add(reportSums[i])
        }

        return toReturn.toTypedArray()
    }

    private fun updateDisplay(summaries: Array<TextView>, editBtns: Array<Button>, deleteBtns: Array<Button>) {

        // Load the initial reports
        val reportsList: Array<ReportSummary> = load5Reports() // Load the next 5 reports from this.reportIndex to this.reportIndex + 4

        // Determine if the 'Next' button needs to be visible ( remaining reports > 5 )
        if (this.totalReports - this.reportIndex > 5) {
            next.isVisible = true
            next.isClickable = true
        }
        else {
            next.isVisible = false
            next.isClickable = false
        }

        // Determine if the 'Prev' button needs to be visible ( this.reportIndex > 0 )
        if (this.reportIndex > 0) {
            back.isVisible = true
            back.isClickable = true
        }
        else {
            back.isVisible = false
            back.isClickable = false
        }

        // Set all report displays to not be visible before updating
        for (report in 0 until 5) {
            summaries[report].isVisible = false

            editBtns[report].isVisible = false
            editBtns[report].isClickable = false

            deleteBtns[report].isVisible = false
            deleteBtns[report].isClickable = false
        }

        // Update info for each needed report display then toggle back to visible
        for (report in 0 until reportsList.size) {
            // Sets the text to a format for the summary. This should wrap text... but we'll see in time if that's true
            summaries[report].text = String.format("User:\t%s\nDate:\t%s\n    Title:%s", reportsList[report].getReportUser(), reportsList[report].getDate().toString(), reportsList[report].getReportTitle())

            editBtns[report].setOnClickListener {
                val intent = Intent(this, ViewEditReport ::class.java)
                intent.putExtra("CreatingNew", false)
                // Load the report
                var rep = Report()
                rep.loadReport(reportSums[report].getReportId(), this)
                intent.putExtra("Report", rep)
                startActivity(intent)
            }

            deleteBtns[report].setOnClickListener {
                rs.deleteSummary(reportsList[report].getReportId())
                this.reportSums = rs.loadAllSummaries()
                this.totalReports = reportSums.size
                updateDisplay(summaries, editBtns, deleteBtns)
            }

            summaries[report].isVisible = true
            editBtns[report].isVisible = true
            deleteBtns[report].isVisible = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reportlistview)

        // This is just... stupid... but we'll stick to the design contract of not interfacing directly w/ the DB
        this.rs = ReportSummary("", "", 0, "", "")
        rs.setContext(this)

        this.reportSums = rs.loadAllSummaries()
        this.totalReports = reportSums.size

        val summaries = arrayOf(reportSummary0, reportSummary1, reportSummary2, reportSummary3, reportSummary4)
        val editBtns = arrayOf(editReport0, editReport1, editReport2, editReport3, editReport4)
        val deleteBtns = arrayOf(deleteReport0, deleteReport1, deleteReport2, deleteReport3, deleteReport4)

        updateDisplay(summaries, editBtns, deleteBtns)

        this.appTitle.setOnClickListener {

            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)

        }

        next.setOnClickListener {
            this.reportIndex += 5
            updateDisplay(summaries, editBtns, deleteBtns)
        }

        back.setOnClickListener {
            this.reportIndex -= 5
            updateDisplay(summaries, editBtns, deleteBtns)
        }
    }
}