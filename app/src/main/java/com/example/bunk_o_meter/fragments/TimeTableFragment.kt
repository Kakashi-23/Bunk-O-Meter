 package com.example.bunk_o_meter.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.R.drawable.error_layout
import com.example.bunk_o_meter.TimeTableRepository
import com.example.bunk_o_meter.adapters.DayAndTimeAdapter
import com.example.bunk_o_meter.database.TimeTableEntity
import com.example.bunk_o_meter.utils.CommonUtilities
import com.example.bunk_o_meter.viewModel.ScheduleViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime24
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime24PickedListener

 class TimeTableFragment : Fragment(){
     private  val TAG = "TimeTableFragment"
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
        subjectName=view.findViewById(R.id.subjectNameAdd)
        dayAndTimeRecycler=view.findViewById(R.id.addRecycler)
        fab=view.findViewById(R.id.fabAdd)
        setHasOptionsMenu(true)
        fab.setOnClickListener {
            dayList.add("")
            dayAndTimeAdapter.notifyDataSetChanged()
        }

        displaySchedule()

        // swipe to delete functionality
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dayList.removeAt(viewHolder.adapterPosition)
                dayAndTimeAdapter.notifyDataSetChanged()
            }

        }).attachToRecyclerView(dayAndTimeRecycler)

        return view
    }

    private fun displaySchedule() {
        dayAndTimeRecycler.layoutManager=LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL,false)
        dayAndTimeAdapter=DayAndTimeAdapter(dayList,requireContext())
        dayAndTimeRecycler.adapter=dayAndTimeAdapter
    }

     override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
         inflater.inflate(R.menu.save_menu,menu)
         super.onCreateOptionsMenu(menu, inflater)
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if (item.itemId == R.id.save_data){
             if(subjectName.text.isNullOrEmpty()){
                 subjectName.error="Please enter the subject"
             } else{
                 saveData(subjectName.text!!.toString())
             }
         }
         return super.onOptionsItemSelected(item)
     }

     private fun saveData(subjectName:String) {
         if (dayList.size!=0) {
             for (i in 0 until dayList.size) {
                 val view = dayAndTimeRecycler.findViewHolderForAdapterPosition(i)!!.itemView
                 val day:TextView= view.findViewById(R.id.dayShow)
                 val startTime: TextInputEditText = view.findViewById(R.id.startTime)
                 val endTime: TextInputEditText = view.findViewById(R.id.endTime)
                 if (!day.text.isNullOrBlank() || !day.text.isNullOrEmpty()){
                     if (!startTime.text.isNullOrEmpty() || !startTime.text.isNullOrBlank()){
                         if (!endTime.text.isNullOrEmpty() || !endTime.text.isNullOrBlank()){
                             pushDataToDB(day.text.toString(),
                             startTime.text!!.toString(),
                             endTime.text!!.toString(),
                             subjectName,
                             i
                             )
                         }else {
                             endTime.error = "Enter Time"
                         }
                     }else{
                         startTime.error="Enter Time"
                     }
                 }else{
                     day.error = "Enter Day"
                 }

             }
         }else {
             CommonUtilities.showToast(requireActivity(),"Enter Schedule")
         }

     }

     private fun pushDataToDB(day:String,startTime:String,endTime:String,subjectName: String,i:Int) {
         val timeTableEntity=TimeTableEntity(day,subjectName,startTime,endTime)
         val viewModel=ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
         if (viewModel.isExists(timeTableEntity)){
             CommonUtilities.showToast(requireContext(),"Data already Exists")
             dayAndTimeRecycler.findViewHolderForAdapterPosition(i)!!.itemView.
                 findViewById<ConstraintLayout>(R.id.scheduleLayout).
                 setBackgroundResource(error_layout)
             return
         }
         val status=viewModel.insert(timeTableEntity)
         if (status){
             CommonUtilities.showToast(requireContext(),"Saved")
         }
     }



     override fun onDestroy() {
         dayList.clear()
         super.onDestroy()
     }
 }