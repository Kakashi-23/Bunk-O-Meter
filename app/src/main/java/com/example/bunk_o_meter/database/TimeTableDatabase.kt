package com.example.bunk_o_meter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TimeTableEntity::class],version = 1)
abstract class TimeTableDatabase:RoomDatabase() {
    abstract fun timeTableDao():TimeTableDAO
    companion object{
        @Volatile
        private var databaseInstance:TimeTableDatabase?=null
       fun getDatabase(context:Context):TimeTableDatabase{
       /*  if(databaseInstance == null){
            val instance = Room.databaseBuilder(context.applicationContext,
                 TimeTableDatabase::class.java,
                 "Schedule"
             ).build()
         databaseInstance=instance
         }
          return databaseInstance*/
           return databaseInstance ?: synchronized(this){
               val instance=Room.databaseBuilder(context.applicationContext,
                   TimeTableDatabase::class.java,
                   "Schedule"
               ).
               fallbackToDestructiveMigration()
                   .build()
               databaseInstance=instance
               instance
           }
      }
    }
}