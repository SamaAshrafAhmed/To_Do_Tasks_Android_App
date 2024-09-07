package com.example.to_dolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var title:String,
    var task:String,
    var priority:Int,
    var done:Boolean)
