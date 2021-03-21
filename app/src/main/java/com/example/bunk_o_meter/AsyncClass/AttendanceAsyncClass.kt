package com.example.bunk_o_meter.AsyncClass

import android.os.AsyncTask
import android.provider.SyncStateContract
import com.example.bunk_o_meter.AttendanceDatabase.AttendanceDao
import com.example.bunk_o_meter.AttendanceDatabase.AttendanceEntity
import com.example.bunk_o_meter.utils.Constants

class AttendanceAsyncClass(val dao:AttendanceDao, val attendanceInfo:AttendanceEntity, val code:Int):
    AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg p0: Void?): Boolean {
        when(code){
            Constants.Insert-> {
                 dao.insertEntity(attendanceInfo)
                return true
            }
            Constants.Delete->{
                dao.deleteAttendance(attendanceInfo)
                return true
            }
        }
        return false
    }
}