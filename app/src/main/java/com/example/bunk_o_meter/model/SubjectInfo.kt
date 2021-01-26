package com.example.bunk_o_meter.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class SubjectInfo(
    @SerializedName("Day")
    val day:String,
    @ColumnInfo(name = "Subject")
    val Subject:String,
    @ColumnInfo(name = "Start_Time")
    val StartTime:String,
    @ColumnInfo(name = "End_Time")
    val EndTime:String
)
