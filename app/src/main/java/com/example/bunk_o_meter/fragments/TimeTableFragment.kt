 package com.example.bunk_o_meter.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.adapters.DayAndTimeAdapter
import com.example.bunk_o_meter.utils.CommonUtilities
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime24
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime24PickedListener

 class TimeTableFragment : Fragment(){

    lateinit var dayAndTimeRecycler: RecyclerView
    lateinit var subjectName:TextInputEditText
    lateinit var dayAndTimeAdapter: DayAndTimeAdapter
    lateinit var fab:FloatingActionButton
    private val dayList= arrayListOf<String>("1")

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
        setHasOptionsMenu(true)
        subjectName=view.findViewById(R.id.subjectNameAdd)
        dayAndTimeRecycler=view.findViewById(R.id.addRecycler)
        fab=view.findViewById(R.id.fabAdd)
        displaySchedule()
        fab.setOnClickListener {
            dayList.add("")
            dayAndTimeAdapter.notifyDataSetChanged()
        }
        return view
    }

    private fun displaySchedule() {
        dayAndTimeRecycler.layoutManager=LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        dayAndTimeAdapter=DayAndTimeAdapter(dayList,requireContext())
        dayAndTimeRecycler.adapter=dayAndTimeAdapter
    }

     override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
         inflater.inflate(R.menu.save_menu,menu)
         super.onCreateOptionsMenu(menu, inflater)
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if (item.itemId == R.id.save_data){
             CommonUtilities.showToast(requireContext(),"Saved")
         }
         return super.onOptionsItemSelected(item)
     }

     override fun onStop() {
         dayList.clear()
         super.onStop()
     }

     override fun onDestroy() {
         dayList.clear()
         super.onDestroy()
     }
 }