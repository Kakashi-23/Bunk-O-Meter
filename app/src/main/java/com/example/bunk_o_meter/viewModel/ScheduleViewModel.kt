package com.example.bunk_o_meter.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.bunk_o_meter.TimeTableRepository
import com.example.bunk_o_meter.database.TimeTableEntity

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private var scheduleRepository=TimeTableRepository(application)
    private var fullSchedule=scheduleRepository.getAllInfo()

    public fun insert(entity:TimeTableEntity){
        scheduleRepository.insert(entity)
    }
    public fun delete(entity:TimeTableEntity){
        scheduleRepository.delete(entity)
    }
    public fun update(entity:TimeTableEntity){
        scheduleRepository.update(entity)
    }
    public fun deleteAll(entity:TimeTableEntity){
        scheduleRepository.deleteAll(entity)
    }
    public fun getSubject(day: String,time: String){
        scheduleRepository.getSubject(day,time)
    }
    fun getAllSchedule(): LiveData<List<TimeTableEntity>> {
        return fullSchedule
    }
}