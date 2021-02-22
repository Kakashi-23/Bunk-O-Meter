package com.example.bunk_o_meter.utils

import com.example.bunk_o_meter.model.SubjectInfo
import com.google.android.gms.maps.model.LatLng

object Constants {
    const val Insert=1
    const val Update=2
    const val Delete=3
    const val DeleteAll=4
    const val GET_SUBJECT=5
    const val GET_Entity=6
    const val SUBJECT=7
    const val locationPermission=8
 lateinit var lectureHallLocation:LatLng
    var timeList= arrayListOf<SubjectInfo>()
    val dayList= arrayListOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
    )
}