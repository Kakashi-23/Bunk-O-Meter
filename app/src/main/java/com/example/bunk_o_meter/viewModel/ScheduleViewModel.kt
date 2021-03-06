package com.example.bunk_o_meter.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.bunk_o_meter.repositories.TimeTableRepository
import com.example.bunk_o_meter.TimeTableDatabase.TimeTableEntity

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private var scheduleRepository= TimeTableRepository(application)
    private var fullSchedule=scheduleRepository.getAllInfo()

    public fun insert(entity:TimeTableEntity): Boolean {
      return scheduleRepository.insert(entity)
    }
    public fun delete(entity:TimeTableEntity): Boolean {
        return scheduleRepository.delete(entity)
    }
    public fun update(entity:TimeTableEntity): Boolean {
        return  scheduleRepository.update(entity)
    }
    public fun deleteAll(entity:TimeTableEntity): Boolean {
        return  scheduleRepository.deleteAll(entity)
    }
    public fun getSubject(entity: TimeTableEntity): TimeTableEntity? {
       return scheduleRepository.getSubject(entity.day,entity.StartTime,entity.Subject)
    }
    fun getAllSchedule(): LiveData<List<TimeTableEntity>> {
        return fullSchedule
    }
    fun isExists(entity: TimeTableEntity):Boolean{
        return scheduleRepository.isExists(entity)
    }
    fun getEntity(entity: TimeTableEntity):TimeTableEntity{
        return scheduleRepository.getEntity(entity)
    }
    fun getAllSubject():LiveData<List<String>>{
        return scheduleRepository.getAllSubject()
    }
}