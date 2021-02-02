package com.example.bunk_o_meter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.google.android.material.textfield.TextInputEditText
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime24
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime24PickedListener
import java.lang.ref.WeakReference
import kotlin.math.E


class DayAndTimeAdapter(private val dayList:ArrayList<String>,
                        private val context:Context): RecyclerView.Adapter<DayAndTimeAdapter.DayViewHolder>() {

private val StartTime=1
    private val EndTime=2
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
        setSpinnerAdapter(holder)
       holder.startTime.setOnClickListener {  setTimePicker(holder,StartTime) }
        holder.endTime.setOnClickListener {setTimePicker(holder,EndTime)}
    }

    override fun getItemCount(): Int {
       return dayList.size
    }
    private fun setSpinnerAdapter(holder:DayViewHolder){
        val arrayAdapter=ArrayAdapter.createFromResource(WeakReference(context).get()!!,
            R.array.Days,
            android.R.layout.simple_spinner_item)
       arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.daySpinner.adapter=arrayAdapter
    }

    private fun setTimePicker(holder:DayViewHolder,type:Int) {
        val timePicker = CmtpTimeDialogFragment.newInstance()
        timePicker.setInitialTime24(13, 0)
       val manager= (context as AppCompatActivity).supportFragmentManager
        timePicker.show(manager,"Time")
        timePicker.setOnTime24PickedListener {
            if (type==StartTime){
                holder.startTime.setText(it.toString())
            }else if(type== EndTime){
                holder.endTime.setText(it.toString())
            }
        }


    }
}