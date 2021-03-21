package com.example.bunk_o_meter.AttendanceDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AttendanceEntity::class],version = 1)
abstract class AttendanceDatabase: RoomDatabase() {
    abstract fun getAttendanceDao():AttendanceDao
  companion object{
      @Volatile
      private var databaseInstance:AttendanceDatabase?=null

      fun getDatabaseInstance(context: Context):AttendanceDatabase{
          return databaseInstance ?: synchronized(this){
              val instance = Room.databaseBuilder(context.applicationContext,
              AttendanceDatabase::class.java,
              "Attendace"
              ).fallbackToDestructiveMigration().build()

              databaseInstance = instance
              instance
          }
      }
  }

}