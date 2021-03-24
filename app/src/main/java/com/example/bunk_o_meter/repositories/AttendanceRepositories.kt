package com.example.bunk_o_meter.repositories

import android.app.Application
import android.os.AsyncTask
import android.provider.SyncStateContract
import androidx.lifecycle.LiveData
import com.example.bunk_o_meter.AsyncClass.AttendanceAsyncClass
import com.example.bunk_o_meter.AttendanceDatabase.AttendanceDatabase
import com.example.bunk_o_meter.AttendanceDatabase.AttendanceEntity
import com.example.bunk_o_meter.utils.Constants

class AttendanceRepositories(application: Application) {
    val database=AttendanceDatabase.getDatabaseInstance(application)
    val attendanceDao=database.getAttendanceDao()
   lateinit var task:AsyncTask<Void,Void,Boolean>

    // methods to do different functions

    fun insert(entity: AttendanceEntity):Boolean{
        task = AttendanceAsyncClass(attendanceDao,entity,Constants.Insert).execute()
        return task.get()!!
    }
    fun delete(entity: AttendanceEntity):Boolean{
        task = AttendanceAsyncClass(attendanceDao,entity,Constants.Delete).execute()
        return task.get()!!
    }

    fun getAttendanceInfo():LiveData<List<AttendanceEntity>>{
        return attendanceDao.getAttendance()
    }

    fun update(entity: AttendanceEntity){
        // update function
    }

}