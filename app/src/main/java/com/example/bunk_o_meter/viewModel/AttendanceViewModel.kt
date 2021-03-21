package com.example.bunk_o_meter.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.bunk_o_meter.AttendanceDatabase.AttendanceEntity
import com.example.bunk_o_meter.repositories.AttendanceRepositories

class AttendanceViewModel(application: Application):AndroidViewModel(application) {
    private var attendanceRepo=AttendanceRepositories(application)

    fun getAllInfo():LiveData<ArrayList<AttendanceEntity>>{
        return attendanceRepo.getAttendanceInfo()
    }

    fun insert(entity: AttendanceEntity):Boolean{
        return attendanceRepo.insert(entity)
    }

    fun delete(entity: AttendanceEntity):Boolean{
        return attendanceRepo.insert(entity)
    }

    fun update(entity: AttendanceEntity){
        // update function
    }
}