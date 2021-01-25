package com.example.bunk_o_meter.Database

import androidx.room.*
import com.example.bunk_o_meter.Model.SubjectInfo

@Dao
interface DAO {
    @Insert
    fun InsertTimeTable(timeTable:TimeTableEntity)

    @Query("SELECT Subject and End_Time FROM time_table WHERE Day = :currentDay and Start_Time = :currentTime")
    fun getSubject(currentDay:String, currentTime: String): SubjectInfo

    @Delete
    fun deleteTimeTable(timeTable: TimeTableEntity)

}