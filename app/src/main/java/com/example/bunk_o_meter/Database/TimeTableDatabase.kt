package com.example.bunk_o_meter.Database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase

@Database(entities = [TimeTableEntity::class],version = 1)
abstract class TimeTableDatabase:RoomDatabase() {
    abstract fun dataDao():DAO
}