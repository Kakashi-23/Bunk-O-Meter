package com.example.bunk_o_meter.AttendanceDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEntity(entity: AttendanceEntity)

    @Delete
    fun deleteAttendance(entity :AttendanceEntity)

    @Query("SELECT * FROM Attendance")
    fun getAttendance():LiveData<List<AttendanceEntity>>

}