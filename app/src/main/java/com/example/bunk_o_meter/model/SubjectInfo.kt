package com.example.bunk_o_meter.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class SubjectInfo(
    val day:String,
    val Subject:String,
    val StartTime:String,
    val EndTime:String
)
