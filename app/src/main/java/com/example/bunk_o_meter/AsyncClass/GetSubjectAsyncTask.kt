package com.example.bunk_o_meter.AsyncClass

import android.os.AsyncTask
import com.example.bunk_o_meter.TimeTableDatabase.TimeTableDAO
import com.example.bunk_o_meter.TimeTableDatabase.TimeTableEntity
import com.example.bunk_o_meter.utils.Constants

class GetSubjectAsyncTask(val dao: TimeTableDAO, val day: String, val startTime:String, val subject: String, val code:Int):
        AsyncTask<Void, Void, TimeTableEntity>() {
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