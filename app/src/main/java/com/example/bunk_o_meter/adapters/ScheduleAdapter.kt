package com.example.bunk_o_meter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.database.TimeTableEntity
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_add_time_table.view.*

class ScheduleAdapter(val list:ArrayList<TimeTableEntity>): RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       var subjectName: TextInputEditText = itemView.findViewById<TextInputEditText>(R.id.subjectName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.subject_layout
           ,parent
           ,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val listItem= list[position]
        holder.subjectName.setText(listItem.Subject)

    }

    override fun getItemCount(): Int {
       return list.size
    }

}