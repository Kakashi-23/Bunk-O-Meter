package com.example.bunk_o_meter.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class SubjectInfo(
    var day:String?,
    var Subject:String?,
    var StartTime:String?,
    var EndTime:String?
)
