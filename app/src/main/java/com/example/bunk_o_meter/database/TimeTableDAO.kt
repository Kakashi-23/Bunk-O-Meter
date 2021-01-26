package com.example.bunk_o_meter.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TimeTableDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTimeTable(timeTable:TimeTableEntity)

    @Query("SELECT * FROM Time_Table WHERE Day = :currentDay and Start_Time = :currentTime")
    fun getSubject(currentDay:String, currentTime: String): LiveData<TimeTableEntity>

    @Delete
    fun deleteTimeTable(timeTable: TimeTableEntity)

    @Update
    fun update(timeTable: TimeTableEntity)

    @Query( "DELETE FROM Time_Table")
    fun deleteAll()

    @Query("SELECT * FROM Time_Table")
    fun getAllInfo():LiveData<TimeTableEntity>

}