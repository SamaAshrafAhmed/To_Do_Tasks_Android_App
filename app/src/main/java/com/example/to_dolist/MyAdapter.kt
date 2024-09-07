package com.example.to_dolist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.to_dolist.databinding.TodoTaskLayoutBinding
import com.example.to_dolist.databinding.TodoTaskPriorityBinding


class MyAdapter(
    val viewModel: ToDoViewModel,
    val onEditTask:(ToDoTask) -> Unit,
    val onDoneTask:(ToDoTask) -> Unit,
    val onDeleteTask:(ToDoTask) -> Unit,
    )
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class viewHolderLeastPriority(val binding:TodoTaskLayoutBinding):ViewHolder(binding.root){
        fun bind(task:ToDoTask){
            binding.toDo = task
            if(task.done==false){
                binding.taskName.setTextColor(Color.rgb(104,153,98))
                binding.priorityView.setBackgroundColor(Color.rgb(104,153,98))
                binding.taskCv.setStrokeColor(Color.rgb(104,153,98))
                binding.taskContent.setTextColor(Color.rgb(127,157,124))
                binding.doneTask.isVisible = true
                binding.doneTask.isChecked = false
                binding.doneIcon.isVisible = false
                binding.editTask.isVisible = true
            }else{
                binding.taskName.setTextColor(Color.GRAY)
                binding.priorityView.setBackgroundColor(Color.GRAY)
                binding.taskCv.setStrokeColor(Color.GRAY)
                binding.taskContent.setTextColor(Color.GRAY)
                binding.doneTask.isVisible = false
                binding.doneTask.isChecked = true
                binding.doneIcon.isVisible = true
                binding.editTask.isVisible = false
            }
            binding.doneTask.setOnClickListener{
                task.done = true
                onDoneTask(task)
                notifyItemChanged(adapterPosition)

            }


            binding.deleteBtn.setOnClickListener{
                viewModel.removeTask(task)
                onDeleteTask(task)
            }

            binding.editTask.setOnClickListener{
                onEditTask(task)

            }
        }
    }
    inner class viewHolderMostPriority(val binding:TodoTaskPriorityBinding):ViewHolder(binding.root){
        fun bind(task:ToDoTask){
            binding.toDo = task
            if(task.done==false){
                binding.taskName.setTextColor(Color.rgb(153,98,104))
                binding.priorityView.setBackgroundColor(Color.rgb(153,98,104))
                binding.taskCv.setStrokeColor(Color.rgb(153,98,104))
                binding.taskContent.setTextColor(Color.rgb(162,129,133))
                binding.doneTask.isVisible = true
                binding.doneTask.isChecked = false
                binding.doneIcon.isVisible = false
                binding.editTask.isVisible = true
            }else{
                binding.taskName.setTextColor(Color.GRAY)
                binding.priorityView.setBackgroundColor(Color.GRAY)
                binding.taskCv.setStrokeColor(Color.GRAY)
                binding.taskContent.setTextColor(Color.GRAY)
                binding.doneTask.isVisible = false
                binding.doneTask.isChecked = true
                binding.doneIcon.isVisible = true
                binding.editTask.isVisible = false
            }
            binding.doneTask.setOnClickListener{
                task.done = true
                onDoneTask(task)
                notifyItemChanged(adapterPosition)

            }

            binding.deleteBtn.setOnClickListener{
                viewModel.removeTask(task)
                onDeleteTask(task)
            }
            binding.editTask.setOnClickListener{
                onEditTask(task)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return viewModel.toDoList.value?.get(position)!!.priority
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            1 -> {
               val binding = TodoTaskPriorityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return viewHolderMostPriority(binding)
            }
            2 -> {
                val binding = TodoTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return viewHolderLeastPriority(binding)
            }
            else -> throw IllegalAccessException("Invalid Priority")
        }
    }

    override fun getItemCount(): Int {
        return  viewModel.toDoList.value!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            1 -> (holder as viewHolderMostPriority).bind(viewModel.toDoList.value?.get(position)!!)
            2 -> (holder as viewHolderLeastPriority).bind(viewModel.toDoList.value?.get(position)!!)
            else -> throw IllegalAccessException("invalid")
        }

    }

}