package com.example.bunk_o_meter

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.bunk_o_meter.database.TimeTableDAO
import com.example.bunk_o_meter.database.TimeTableDatabase
import com.example.bunk_o_meter.database.TimeTableEntity
import com.example.bunk_o_meter.utils.Constants

class TimeTableRepository(application: Application) {
    val database=TimeTableDatabase.getDatabase(application)
    var scheduleDao:TimeTableDAO=database.timeTableDao()
    fun insert(entity:TimeTableEntity){
     ScheduleAsyncTask(scheduleDao,Constants.Insert).execute(entity)
    }
    fun delete(entity:TimeTableEntity){
        ScheduleAsyncTask(scheduleDao,Constants.Delete).execute(entity)
    }
    fun update(entity:TimeTableEntity){
        ScheduleAsyncTask(scheduleDao,Constants.Update).execute(entity)
    }
    fun deleteAll(entity: TimeTableEntity){
        ScheduleAsyncTask(scheduleDao,Constants.DeleteAll).execute(entity)
    }
    fun getAllInfo():LiveData<TimeTableEntity>{
        return scheduleDao.getAllInfo()
    }
    fun getSubject(day: String,time: String):LiveData<TimeTableEntity>{
        return scheduleDao.getSubject(day,time)
    }

    // Async task
    private class ScheduleAsyncTask(dao: TimeTableDAO,resultCode:Int): AsyncTask<TimeTableEntity,Void,Boolean>() {
        val scheduledao=dao
        val code=resultCode
        override fun doInBackground(vararg entity: TimeTableEntity?):Boolean {
            when(code){
                Constants.Insert->{
                    scheduledao.insertTimeTable(entity[0]!!)
                    return true
                }
                Constants.Delete->{
                    scheduledao.deleteTimeTable(entity[0]!!)
                    return true
                }
                Constants.Update->{
                    scheduledao.update(entity[0]!!)
                    return true
                }
                Constants.DeleteAll->{
                    scheduledao.deleteAll()
                    return true
                }
            }
            return false
        }

    }
}