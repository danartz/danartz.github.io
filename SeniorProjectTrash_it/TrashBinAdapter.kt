package com.example.trash_it

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.row_main.view.*

class TrashBinAdapter(val mCtx: Context, val layoutResId: Int, val trashBinNameList: List<String>, val trashBinRecentEntry: MutableList<TrashData>):
    ArrayAdapter<String>(mCtx, layoutResId, trashBinNameList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val textViewName = view.findViewById<TextView>(R.id.textViewBinName)
        val trashBinImage = view.findViewById<ImageView>(R.id.trashBinImage)
        val trashBinImageWarn = view.findViewById<ImageView>(R.id.trashBinWarn)
        trashBinImageWarn.visibility = View.INVISIBLE
        val backgroundColor = Color.parseColor("#DDE3DA")
        view.setBackgroundColor(backgroundColor)
        view.setPadding(5, 40, 5, 40)

        if(trashBinRecentEntry[position].height.toFloat() / trashBinRecentEntry[position].maxHeight.toFloat() >= .80 ||
            trashBinRecentEntry[position].weight.toFloat() / trashBinRecentEntry[position].maxWeight.toFloat() >= .80) {
            trashBinImage.visibility = View.INVISIBLE
            trashBinImageWarn.visibility = View.VISIBLE
        }


        val trash = trashBinNameList[position]
        textViewName.text = trash

        return view
    }

}