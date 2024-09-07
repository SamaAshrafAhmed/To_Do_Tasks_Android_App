package com.example.to_dolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToDoViewModel:ViewModel() {
   private var _toDoList = MutableLiveData<List<ToDoTask>>()
     val toDoList: LiveData<List<ToDoTask>> get() = _toDoList
     init {
         _toDoList.value = mutableListOf()
     }

     fun addTask(task: ToDoTask){
          _toDoList.value  = _toDoList.value?.toMutableList()?.apply {
               add(task)
          }

     }
     fun removeTask(task: ToDoTask){
          _toDoList.value = _toDoList.value?.toMutableList()?.apply {
               remove(task)
          }
     }
    fun editTask(position:Int,title:String, content:String){
        _toDoList.value = _toDoList.value?.toMutableList()?.apply {
            get(position).title = title
            get(position).task = content
        }
            }

    fun setList(list: MutableList<ToDoTask>) {
        _toDoList.value = list
    }

}