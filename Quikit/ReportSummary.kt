package com.example.quickreport

import android.content.Context
import android.widget.Toast

/**
 * Stores simplified report details for ReportListView screen
 */
class ReportSummary(dateField: String, summaryField: String, reportIdField: Int, reportTitleField: String, reportUserField: String) {
    private lateinit var db: DatabaseManager
    private lateinit var contx: Context

    private var date: String? = null
    private var summary: String? = null
    private var reportId = 0
    private var reportTitle: String? = null
    private var reportUser: String? = null

    fun getReportTitle(): String?{return this.reportTitle}
    fun getReportUser(): String?{return this.reportUser}
    fun getDate(): String?{return this.date}
    fun getSummary(): String?{return this.summary}
    fun getReportId(): Int{return this.reportId}



    init{
        this.date = dateField
        this.reportId = reportIdField
        this.summary = summaryField
        this.reportTitle = reportTitleField
        this.reportUser = reportUserField
    }

    fun setContext(context: Context) {
        this.contx = context
        this.db = DatabaseManager(context)
    }

    fun deleteSummary(id: Int) {
        db.deleteReportEntry(id)
        var report = Report()
        report.loadReport(id, contx)
        report.deleteSelf(contx)
    }

    fun loadAllSummaries(): ArrayList<ReportSummary> {
        try {
            return db.queryAllSummary()
        }
        catch(e: UninitializedPropertyAccessException) {
            Toast.makeText(contx, "No entries in Database present", Toast.LENGTH_SHORT).show()
            return ArrayList()
        }
    }
}