package com.example.trash_it

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TrashBinHistoryAdapter (context: Context, count: Int, trashDataList: ArrayList<TrashData>): BaseAdapter() {
    private val mContext: Context
    private var trashList: ArrayList<TrashData>
    private var count: Int = 0

    init{
        mContext = context
        trashList = ArrayList<TrashData>()
        trashList.addAll(trashDataList)
        this.count = count
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val rowMain = layoutInflater.inflate(R.layout.row_main, parent, false)
        val trashDate = rowMain.findViewById<TextView>(R.id.trashDate)
        trashDate.text = "Date: " + trashList[position].dateTime
        val trashWeight = rowMain.findViewById<TextView>(R.id.trashWeight)
        trashWeight.text = "Weight: " + trashList[position].weight
        val trashHeight = rowMain.findViewById<TextView>(R.id.trashHeight)
        trashHeight.text = "Height: " + trashList[position].height

        val backgroundColor = Color.parseColor("#DDE3DA")
        rowMain.setBackgroundColor(backgroundColor)

        return rowMain
    }

    override fun getItem(position: Int): Any {
        return "test string"
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
        return this.count
    }

}