package com.example.bunk_o_meter.utils

import com.example.bunk_o_meter.model.SubjectInfo

object Constants {
    const val Insert=1
    const val Update=2
    const val Delete=3
    const val DeleteAll=4
    const val GET_SUBJECT=5
    var timeList= arrayListOf<SubjectInfo>()
    val dayList= arrayListOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
    )
}