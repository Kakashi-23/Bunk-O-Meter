package com.example.bunk_o_meter.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.bunk_o_meter.AsyncClass.GetSubjectAsyncTask
import com.example.bunk_o_meter.AsyncClass.ScheduleAsyncClass
import com.example.bunk_o_meter.TimeTableDatabase.TimeTableDAO
import com.example.bunk_o_meter.TimeTableDatabase.TimeTableDatabase
import com.example.bunk_o_meter.TimeTableDatabase.TimeTableEntity
import com.example.bunk_o_meter.utils.Constants

class TimeTableRepository(application: Application) {
    val database=TimeTableDatabase.getDatabase(application)
    var scheduleDao:TimeTableDAO=database.timeTableDao()
    fun insert(entity:TimeTableEntity): Boolean {
    val task=  ScheduleAsyncClass(scheduleDao,Constants.Insert).execute(entity)
        return task.get()!!
    }
    fun delete(entity:TimeTableEntity): Boolean{
        val task=  ScheduleAsyncClass(scheduleDao,Constants.Delete).execute(entity)
        return task.get()!!
    }
    fun update(entity:TimeTableEntity): Boolean{
        val task=  ScheduleAsyncClass(scheduleDao,Constants.Update).execute(entity)
        return task.get()!!
    }
    fun deleteAll(entity: TimeTableEntity): Boolean{
        val task= ScheduleAsyncClass(scheduleDao,Constants.DeleteAll).execute(entity)
        return task.get()!!
    }
    fun getAllInfo(): LiveData<List<TimeTableEntity>> {
        return scheduleDao.getAllInfo()
    }
    fun getSubject(day: String,time: String,subject: String):TimeTableEntity?{
        val task = GetSubjectAsyncTask(scheduleDao,day,time,subject,Constants.GET_SUBJECT).execute()
        return task.get()

    }
    fun isExists(entity: TimeTableEntity):Boolean{
        var exists=false
        val scheduleEntity=getSubject(entity.day,entity.StartTime,entity.Subject)
        if (scheduleEntity!=null){
            if(scheduleEntity.Subject == entity.Subject){
                exists=true
            }
        }
        return exists
    }
    fun getEntity(entity: TimeTableEntity):TimeTableEntity{
        val task = GetSubjectAsyncTask(scheduleDao,entity.day,entity.StartTime,entity.Subject,Constants.GET_Entity).execute()
        return task.get()
    }

    fun getAllSubject():LiveData<List<String>>{
     return scheduleDao.getAllSubject()
    }
    /*private fun checkEntity(entity: TimeTableEntity, scheduleEntity: TimeTableEntity): Boolean {
        var exists=false
        if (entity.Subject==scheduleEntity.Subject){
            if (entity.day==scheduleEntity.day){
                if (entity.StartTime==scheduleEntity.StartTime){
                exists = true
                }
            }
        }
        return exists
    }*/
}