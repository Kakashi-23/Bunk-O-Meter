 package com.example.bunk_o_meter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.adapters.DayAndTimeAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

 class TimeTableFragment : Fragment() {

    lateinit var dayAndTimeRecycler: RecyclerView
    lateinit var subjectName:TextInputEditText
    lateinit var dayAndTimeAdapter: DayAndTimeAdapter
    lateinit var fab:FloatingActionButton
    private val dayList= arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_add_time_table,
            container,
            false)
       /* subject=view.findViewById(R.id.subjectLayout)
        scheduleViewModel=ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
        scheduleViewModel.getAllSchedule().observe(viewLifecycleOwner, Observer {
            subject.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            adapter= ScheduleAdapter((it as ArrayList<TimeTableEntity>?)!!)
            subject.adapter=adapter
        })*/
        dayList.add("Monday")
        dayList.add("Tuesday")
        dayList.add("Wednesday")
        dayList.add("Thursday")
        dayList.add("Friday")
        subjectName=view.findViewById(R.id.subjectNameAdd)
        dayAndTimeRecycler=view.findViewById(R.id.addRecycler)
        fab=view.findViewById(R.id.fabAdd)
        displaySchedule()
        return view
    }

    private fun displaySchedule() {
        dayAndTimeRecycler.layoutManager=LinearLayoutManager(activity,
        LinearLayoutManager.VERTICAL,false)
        dayAndTimeAdapter=DayAndTimeAdapter(dayList,requireContext())
        dayAndTimeRecycler.adapter=dayAndTimeAdapter
    }

}