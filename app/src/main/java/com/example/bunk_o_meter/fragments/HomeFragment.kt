package com.example.bunk_o_meter.fragments

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.adapters.SubjectHomeAdapter
import com.example.bunk_o_meter.database.TimeTableDatabase
import com.example.bunk_o_meter.database.TimeTableEntity
import com.example.bunk_o_meter.viewModel.ScheduleViewModel


class HomeFragment : Fragment() {
lateinit var  subjectRecyclerView:RecyclerView
private lateinit var layoutManager:LinearLayoutManager
    lateinit var subjectAdapter: SubjectHomeAdapter
    lateinit var viewModel:ScheduleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_home, container, false)
        subjectRecyclerView=view.findViewById(R.id.subjectRecyclerView)
        layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        subjectAdapter= SubjectHomeAdapter(requireActivity())
        viewModel=ViewModelProviders.of(this@HomeFragment)
            .get(ScheduleViewModel::class.java)
        subjectRecyclerView.layoutManager=layoutManager
        subjectRecyclerView.adapter=subjectAdapter
        viewModel.getAllSubject().observe(viewLifecycleOwner, Observer {
           subjectAdapter.setList(it as ArrayList<String>)
           subjectAdapter.notifyDataSetChanged()
        })

        return view
    }


}