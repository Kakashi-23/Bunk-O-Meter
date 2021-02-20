package com.example.bunk_o_meter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.google.android.material.textview.MaterialTextView


class SubjectHomeAdapter(val context:Context):RecyclerView.Adapter<SubjectHomeAdapter.SubjectViewHolder>() {
   var  subject= arrayListOf<String>()
    class SubjectViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val subjectName:MaterialTextView=view.findViewById(R.id.homeSubjectName)
        val subjectAttendance:MaterialTextView=view.findViewById(R.id.homeSubjectAttendance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        return SubjectViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.subjectName.text=subject[position]
    }

    override fun getItemCount(): Int {
       return subject.size
    }
    fun setList(list:ArrayList<String>){
        this.subject=list
    }
}