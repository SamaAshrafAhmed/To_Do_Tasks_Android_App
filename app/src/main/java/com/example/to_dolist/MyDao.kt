package com.example.to_dolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: ToDoTask)

    @Delete
    fun deleteTask(task: ToDoTask)

    @Update
    fun updateTask(task: ToDoTask)

    @Query("Select * from Tasks")
    fun getTasks(): MutableList<ToDoTask>
}