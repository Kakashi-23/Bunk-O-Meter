package com.example.bunk_o_meter.AsyncClass

import android.os.AsyncTask
import com.example.bunk_o_meter.TimeTableDatabase.TimeTableDAO
import com.example.bunk_o_meter.TimeTableDatabase.TimeTableEntity
import com.example.bunk_o_meter.utils.Constants

class ScheduleAsyncClass(dao: TimeTableDAO, resultCode:Int): AsyncTask<TimeTableEntity, Void, Boolean>() {
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