package com.example.bunk_o_meter.fragments

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.database.TimeTableDatabase
import com.example.bunk_o_meter.database.TimeTableEntity


class HomeFragment : Fragment() {
lateinit var button:Button
lateinit var database: TimeTableDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_home, container, false)

        button=view.findViewById(R.id.button)
        database= TimeTableDatabase.getDatabase(requireActivity())
        button.setOnClickListener {
            PopulateDbAsyncTask(database).execute()
        }
        return view
    }
    class PopulateDbAsyncTask(db:TimeTableDatabase): AsyncTask<Void, Void, Void>() {
        val dao=db.timeTableDao()
        override fun doInBackground(vararg params: Void?): Void? {
            dao.insertTimeTable(
                TimeTableEntity(1,"Monday","Science",
                "9","10")
            )
            dao.insertTimeTable(
                TimeTableEntity(2,"Monday","Science2",
                "8","10")
            )
            return null
        }

    }

}