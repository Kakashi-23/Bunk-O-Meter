package com.example.bunk_o_meter.TimeTableDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Time_Table")
data class TimeTableEntity(
    @ColumnInfo(name = "Day")
    val day:String,
    @ColumnInfo(name = "Subject")
    val Subject:String,
    @ColumnInfo(name = "Start_Time")
    val StartTime:String,
    @ColumnInfo(name = "End_Time")
    val EndTime:String

){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}
