package com.example.to_dolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class TasksDB:RoomDatabase() {
    abstract fun getDao():MyDao

    companion object{
        @Volatile
        private var instance:TasksDB?=null

        @Synchronized
        fun getInstance(contxt:Context):TasksDB{
            if(instance==null){
                val inst = Room.databaseBuilder(contxt.applicationContext, TasksDB::class.java,"TasksDB")
                    .fallbackToDestructiveMigration().build()
                instance =  inst
            }
            return instance!!
        }

    }
}