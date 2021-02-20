package com.example.bunk_o_meter

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.bunk_o_meter.database.TimeTableDAO
import com.example.bunk_o_meter.database.TimeTableDatabase
import com.example.bunk_o_meter.database.TimeTableEntity
import com.example.bunk_o_meter.utils.Constants
import javax.security.auth.Subject

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

    // Async task
    private class ScheduleAsyncTask(dao: TimeTableDAO,resultCode:Int): AsyncTask<TimeTableEntity,Void,Boolean>() {
        val scheduleDao=dao
        val code=resultCode
        override fun doInBackground(vararg entity: TimeTableEntity?):Boolean {
            when(code){
                Constants.Insert->{
                    scheduleDao.insertTimeTable(entity[0]!!)
                    return true
                }
                Constants.Delete->{
                    scheduleDao.deleteTimeTable(entity[0]!!)
                    return true
                }
                Constants.Update->{
                    scheduleDao.update(entity[0]!!)
                    return true
                }
                Constants.DeleteAll->{
                    scheduleDao.deleteAll()
                    return true
                }

            }
            return false
        }

    }
    private class GetSubjectAsyncTask(val dao: TimeTableDAO, val day: String,val startTime:String,val subject: String,val code:Int):AsyncTask<Void,Void,TimeTableEntity>() {
        override fun doInBackground(vararg p0: Void?): TimeTableEntity? {
            when (code) {
                Constants.GET_Entity -> {
                    return dao.getEntity(day, startTime, subject)
                }
                Constants.GET_SUBJECT -> {
                    return dao.getSubject(day, startTime)
                }

            }
            return null
        }
    }
}