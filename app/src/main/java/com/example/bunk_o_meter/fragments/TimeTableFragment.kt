package com.example.bunk_o_meter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.adapters.ScheduleAdapter
import com.example.bunk_o_meter.database.TimeTableEntity
import com.example.bunk_o_meter.viewModel.ScheduleViewModel
import javax.security.auth.Subject

class TimeTableFragment : Fragment() {
    lateinit var scheduleViewModel: ScheduleViewModel
    lateinit var subject: RecyclerView
    lateinit var adapter: ScheduleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_add_time_table,
            container,
            false)
        subject=view.findViewById(R.id.subjectLayout)


        scheduleViewModel=ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        scheduleViewModel.getAllSchedule().observe(viewLifecycleOwner, Observer {
            subject.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            adapter= ScheduleAdapter((it as ArrayList<TimeTableEntity>?)!!)
            subject.adapter=adapter
        })
        return view
    }

}