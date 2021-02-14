package com.example.bunk_o_meter

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.BaseAdapter
import android.widget.ListView
import com.example.bunk_o_meter.adapters.SpinnerAdapter

public class SpinnerDialogView(context: Context,
                             val listData:ArrayList<String>): Dialog(context) {
    val getContext=context
    lateinit var infoList:ListView
    var infoListArray= arrayListOf<String>()
    lateinit var spinnerAdapter: SpinnerAdapter
    var selectedListItem=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
  //      requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_info)

        infoList=findViewById(R.id.infoList)
        for (i in 0 until listData.size){
            infoListArray.add(listData[i])
        }
        spinnerAdapter= SpinnerAdapter(getContext,listData)
        infoList.adapter=spinnerAdapter
        infoList.setOnItemClickListener { adapterView, view, i, l ->
            selectedListItem = listData[i]
            dismiss()
        }



    }
}