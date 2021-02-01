package com.example.bunk_o_meter.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.google.android.material.textfield.TextInputEditText
import java.lang.ref.WeakReference

class DayAndTimeAdapter(private val dayList:ArrayList<String>,val context:Context): RecyclerView.Adapter<DayAndTimeAdapter.DayViewHolder>() {

    class DayViewHolder(view:View):RecyclerView.ViewHolder(view) {
         var daySpinner: Spinner=view.findViewById(R.id.daySpinner)
         var startTime:TextInputEditText=view.findViewById(R.id.startTime)
       var endTime:TextInputEditText=view.findViewById(R.id.endTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.time_table_layout, parent,false)

        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        //TODO(Holder list)
    }

    override fun getItemCount(): Int {
       return dayList.size
    }
    fun setSpinnerAdapter():ArrayAdapter<String>{
        TODO("Set spinner")
    }
}