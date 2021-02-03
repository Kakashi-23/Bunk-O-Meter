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
    fun insert(entity:TimeTableEntity): Boolean {
    val task= ScheduleAsyncTask(scheduleDao,Constants.Insert).execute(entity)
        return task.get()!!
    }
    fun delete(entity:TimeTableEntity): Boolean{
        val task=  ScheduleAsyncTask(scheduleDao,Constants.Delete).execute(entity)
        return task.get()!!
    }
    fun update(entity:TimeTableEntity): Boolean{
        val task=  ScheduleAsyncTask(scheduleDao,Constants.Update).execute(entity)
        return task.get()!!
    }
    fun deleteAll(entity: TimeTableEntity): Boolean{
        val task= ScheduleAsyncTask(scheduleDao,Constants.DeleteAll).execute(entity)
        return task.get()!!
    }
    fun getAllInfo(): LiveData<List<TimeTableEntity>> {
        return scheduleDao.getAllInfo()
    }
    fun getSubject(day: String,time: String):TimeTableEntity?{
        val task = GetSubjectAsyncTask(scheduleDao,day,time).execute()
        return task.get()

    }
    fun isExists(entity: TimeTableEntity):Boolean{
        var exists=false
        val scheduleEntity=getSubject(entity.day,entity.StartTime)
        if (scheduleEntity!=null){
            if(scheduleEntity.Subject == entity.Subject){
                exists=true
            }
        }
        return exists
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
    private class GetSubjectAsyncTask(val dao: TimeTableDAO, val day: String,val startTime:String):AsyncTask<Void,Void,TimeTableEntity>(){
        override fun doInBackground(vararg p0: Void?): TimeTableEntity {
           return dao.getSubject(day,startTime)
        }

    }
}