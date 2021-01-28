package com.example.bunk_o_meter.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [TimeTableEntity::class],version = 4,exportSchema = false)
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