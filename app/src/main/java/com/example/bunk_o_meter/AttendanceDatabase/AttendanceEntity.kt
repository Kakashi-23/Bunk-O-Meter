package com.example.bunk_o_meter.AttendanceDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "Attendance")
data class AttendanceEntity(
    @ColumnInfo(name = "Subject")
    var subject:String,
    @ColumnInfo(name = "Present")
    var present:String,
    @ColumnInfo(name = "Total Classes")
    var total_classes:String,
    @ColumnInfo(name = "Location")
    var location:String
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}