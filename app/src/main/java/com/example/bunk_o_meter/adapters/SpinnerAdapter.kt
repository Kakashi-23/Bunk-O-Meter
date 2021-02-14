package com.example.bunk_o_meter.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.bunk_o_meter.R

class SpinnerAdapter(val context: Context, val listInfo: ArrayList<String>):BaseAdapter() {
    var inflater:LayoutInflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
 //   var tf = Typeface.createFromAsset(context.getAssets(), "fonts/Ubuntu-R.ttf")
 //   var  tfBold = Typeface.createFromAsset(context.getAssets(), "fonts/Ubuntu-B.ttf")
    override fun getCount(): Int {
        return listInfo.size
    }

    override fun getItem(p0: Int): Any {
        return listInfo.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view=inflater.inflate(R.layout.spinner_info_view,null)
        val listData = view.findViewById<TextView>(R.id.list_data_text)
        listData.setText(listInfo[p0].toString())
   //     listData.setTypeface(tf)
        return view
    }
}