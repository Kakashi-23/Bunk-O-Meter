package com.example.bunk_o_meter.TimeTableDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TimeTableDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTimeTable(timeTable:TimeTableEntity)

    @Query("SELECT * FROM Time_Table WHERE Day = :currentDay and Start_Time = :currentTime")
    fun getSubject(currentDay:String, currentTime: String): TimeTableEntity

    @Delete
    fun deleteTimeTable(timeTable: TimeTableEntity)

    @Update
    fun update(timeTable: TimeTableEntity)

    @Query( "DELETE FROM Time_Table")
    fun deleteAll()

    @Query("SELECT * FROM Time_Table")
    fun getAllInfo():LiveData<List<TimeTableEntity>>

    @Query("SELECT * FROM Time_Table WHERE Day = :day and Start_Time=:startTime and Subject =:subject" )
    fun getEntity(day:String,startTime:String,subject: String):TimeTableEntity

    @Query("SELECT Subject FROM Time_Table")
    fun getAllSubject():LiveData<List<String>>
}