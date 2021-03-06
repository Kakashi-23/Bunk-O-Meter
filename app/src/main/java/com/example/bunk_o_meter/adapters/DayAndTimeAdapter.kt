package com.example.bunk_o_meter.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.SpinnerDialogView
import com.example.bunk_o_meter.model.SubjectInfo
import com.example.bunk_o_meter.utils.Constants
import com.google.android.material.textfield.TextInputEditText
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime24
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime24PickedListener
import java.lang.ref.WeakReference
import kotlin.math.E


class DayAndTimeAdapter(private val dayList:ArrayList<String>,
                        private val context:Context): RecyclerView.Adapter<DayAndTimeAdapter.DayViewHolder>(){
    private val StartTime=1
    private val EndTime=2
    class DayViewHolder(view:View):RecyclerView.ViewHolder(view) {
         var startTime:TextInputEditText=view.findViewById(R.id.startTime)
       var endTime:TextInputEditText=view.findViewById(R.id.endTime)
        var showDay :TextInputEditText=view.findViewById(R.id.dayShow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.time_table_layout, parent,false)
        return DayViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.showDay.setOnClickListener {
            setSpinnerAdapter(holder)
        }
       holder.startTime.setOnClickListener {  setTimePicker(holder,StartTime) }
        holder.endTime.setOnClickListener {setTimePicker(holder,EndTime)}
    }


    override fun getItemCount(): Int {
       return dayList.size
    }

    private fun setSpinnerAdapter(holder:DayViewHolder){
      val spinnerDialog = SpinnerDialogView(context,Constants.dayList)
        spinnerDialog.setCancelable(true)
        spinnerDialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        spinnerDialog.setOnDismissListener {
            if (!spinnerDialog.selectedListItem.equals("")){
                holder.showDay.setText(spinnerDialog.selectedListItem)
            }
        }
        spinnerDialog.show()
    }

    private fun setTimePicker(holder:DayViewHolder, type:Int) {
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