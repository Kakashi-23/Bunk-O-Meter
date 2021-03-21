 package com.example.bunk_o_meter.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.R.drawable.error_layout
import com.example.bunk_o_meter.adapters.DayAndTimeAdapter
import com.example.bunk_o_meter.TimeTableDatabase.TimeTableEntity
import com.example.bunk_o_meter.utils.CommonUtilities
import com.example.bunk_o_meter.viewModel.ScheduleViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

 class TimeTableFragment : Fragment(){
    lateinit var dayAndTimeRecycler: RecyclerView
    lateinit var subjectName:TextInputEditText
    lateinit var dayAndTimeAdapter: DayAndTimeAdapter
    lateinit var fab:FloatingActionButton
    private val dayList= arrayListOf<String>("")
     private var entityList = arrayListOf<TimeTableEntity>()
     lateinit var viewModel:ScheduleViewModel

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
        viewModel=ViewModelProviders.of(this@TimeTableFragment).get(ScheduleViewModel::class.java)
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
                val viewList=getViewsFromRecycler(viewHolder.adapterPosition)
                val day=viewList[0]
                val startTime=viewList[1]
                val endTime=viewList[2]
                if (!day.text.isNullOrBlank() or !startTime.text.isNullOrBlank() or !endTime.text.isNullOrBlank() or !subjectName.text.isNullOrBlank()){
                    val timeTableEntity=TimeTableEntity(day.text!!.toString(),subjectName.text.toString(),startTime.text!!.toString(),endTime.text!!.toString())
                    if (viewModel.isExists(timeTableEntity)){
                        showDialog(timeTableEntity,viewHolder.adapterPosition)
                    }
                }else{
                    dayList.removeAt(viewHolder.adapterPosition)
                    dayAndTimeAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                   // TODO("Delete the data cached")
                }
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
         val viewModel=ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
         if (dayList.size != 0) {
             for (i in 0 until dayList.size) {
                 val viewList = getViewsFromRecycler(i)
                 val day = viewList[0]
                 val startTime = viewList[1]
                 val endTime = viewList[2]
                 if (day.text.isNullOrBlank() || day.text.isNullOrEmpty()) {
                     day.error = "Enter Day"
                     entityList.clear()
                     return
                 } else if (startTime.text.isNullOrEmpty() || startTime.text.isNullOrBlank()) {
                     startTime.error = "Enter Time"
                     entityList.clear()
                     return
                 } else if (endTime.text.isNullOrEmpty() || endTime.text.isNullOrBlank()) {
                     endTime.error = "Enter Time"
                     entityList.clear()
                     return
                 } else if (!checkTime(startTime, endTime)) {
                     dayAndTimeRecycler.findViewHolderForAdapterPosition(i)!!.itemView.
                     findViewById<ConstraintLayout>(R.id.scheduleLayout).
                     setBackgroundResource(error_layout)
                     CommonUtilities.showToast(requireContext(),"Time entered is wrong")
                     entityList.clear()
                     return
                 }else if (viewModel.isExists(TimeTableEntity(day.text.toString(), subjectName, startTime.text!!.toString(), endTime.text!!.toString()))){
                     CommonUtilities.showToast(requireContext(),"Data already Exists")
                     dayAndTimeRecycler.findViewHolderForAdapterPosition(i)!!.itemView.
                     findViewById<MaterialCardView>(R.id.scheduleLayout).
                     setBackgroundResource(error_layout)
                     entityList.clear()
                     return
                 }
                 entityList.add( TimeTableEntity(day.text.toString(),
                     subjectName,
                     startTime.text!!.toString(),
                     endTime.text!!.toString()))

                 if (i == dayList.size-1){
                 pushDataToDB(entityList)
                 }

             }
         }else {
             CommonUtilities.showToast(requireContext(),"Enter Schedule")
         }
     }
     private fun checkTime(startTime: TextInputEditText, endTime: TextInputEditText): Boolean {
         val st = startTime.text.toString().split(":")[0].toInt()
         val et=endTime.text.toString().split(":")[0].toInt()
         if (st >= et){
             return false
         }
         return true
     }

     private fun pushDataToDB(entityList:ArrayList<TimeTableEntity>) {
         val viewModel=ViewModelProviders.of(this).get(ScheduleViewModel::class.java)
         for (entity in entityList){
             viewModel.insert(entity)
         }
         CommonUtilities.showToast(requireContext(),"Saved")
     }



     override fun onDestroy() {
         dayList.clear()
         super.onDestroy()
     }
     private fun getViewsFromRecycler(position:Int): ArrayList<TextInputEditText> {
         val view = dayAndTimeRecycler.findViewHolderForAdapterPosition(position)!!.itemView
         val day:TextInputEditText= view.findViewById(R.id.dayShow)
         val startTime: TextInputEditText = view.findViewById(R.id.startTime)
         val endTime: TextInputEditText = view.findViewById(R.id.endTime)
         var viewList= arrayListOf<TextInputEditText>()
         viewList.add(day)
         viewList.add(startTime)
         viewList.add(endTime)
         return viewList
     }

     private fun showDialog(timeTableEntity: TimeTableEntity,position: Int){
         val dialog = AlertDialog.Builder(requireActivity())
         dialog.setTitle("Delete")
         dialog.setMessage("Data exist in database!! Do you want to delete the data?")
         dialog.setPositiveButton("Delete",DialogInterface.OnClickListener { dialogInterface, _ ->
             if (viewModel.delete(viewModel.getEntity(timeTableEntity))){
                 CommonUtilities.showToast(requireContext(),"Deleted")
                 dayList.removeAt(position)
                 dayAndTimeAdapter.notifyItemRemoved(position)
                 dialogInterface.dismiss()
             }else{
                 CommonUtilities.showToast(requireContext(),"Something went wrong!! Try again later")
             }
         })
         dialog.setNegativeButton("Cancel",DialogInterface.OnClickListener{dialogInterface ,_ ->
             dayList.removeAt(position)
             dayAndTimeAdapter.notifyItemRemoved(position)
             dialogInterface.dismiss()
         })
         dialog.show()
     }
 }