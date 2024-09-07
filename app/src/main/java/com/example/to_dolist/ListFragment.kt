package com.example.to_dolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.to_dolist.databinding.AddTaskLayoutBinding
import com.example.to_dolist.databinding.FragmentListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {
    val TAG = "abc"
    lateinit var binding: FragmentListBinding
    lateinit var viewModel: ToDoViewModel
    lateinit var db: TasksDB


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel = ViewModelProvider(this).get(ToDoViewModel::class.java)
        lateinit var list: MutableList<ToDoTask>

        lifecycleScope.launch(Dispatchers.IO) {
            db = TasksDB.getInstance(requireContext())
            list = db.getDao().getTasks()
            withContext(Dispatchers.Main) {
                viewModel.setList(list)
            }
        }


        binding.rvTasks.adapter = MyAdapter(viewModel,
            { editTask(it, viewModel.toDoList.value!!.indexOf(it)) },
            {
                lifecycleScope.launch(Dispatchers.IO) {
                    db.getDao().updateTask(it)
                }
            }, {
                lifecycleScope.launch(Dispatchers.IO) {
                    db.getDao().deleteTask(it)
                }
            })

        // observing
        viewModel.toDoList.observe(viewLifecycleOwner, Observer {
            binding.rvTasks.adapter?.notifyDataSetChanged()
        })

        binding.addTaskBtn.setOnClickListener {
            addTasks()
        }


    }

    fun addTasks() {
        val inflater = LayoutInflater.from(this.requireContext())
        val bindingT = AddTaskLayoutBinding.inflate(inflater)
        val addDialog = AlertDialog.Builder(this.requireContext()).create()
        addDialog.setView(bindingT.root)
        bindingT.cancelBtn.setOnClickListener {
            addDialog.dismiss()
        }
        bindingT.addBtn.setOnClickListener {
            var priority = 2
            if (bindingT.highPriorityChbox.isChecked) {
                priority = 1
            }
            val todo = ToDoTask(
                0,
                bindingT.taskNameEt.text.toString(),
                bindingT.taskContentEt.text.toString(),
                priority,
                false
            )

            lifecycleScope.launch(Dispatchers.IO) {
                db.getDao().addTask(todo)
                val updatedList = db.getDao().getTasks()
                withContext(Dispatchers.Main) {
                    viewModel.setList(updatedList)
                }

            }

            addDialog.dismiss()
        }

        addDialog.show()

    }

    fun editTask(task: ToDoTask, position: Int) {
        val inflater = LayoutInflater.from(this.requireContext())
        val bindingT = AddTaskLayoutBinding.inflate(inflater)
        val editDialog = AlertDialog.Builder(this.requireContext()).create()
        editDialog.setView(bindingT.root)
        bindingT.taskNameEt.setText(task.title.toString())
        bindingT.taskContentEt.setText(task.task.toString())
        var priority = false
        if (task.priority == 1) {
            priority = true
        }
        bindingT.highPriorityChbox.isChecked = priority
        bindingT.highPriorityChbox.isEnabled = false
        bindingT.cancelBtn.setOnClickListener {
            editDialog.dismiss()
        }
        bindingT.addBtn.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {
                task.title = bindingT.taskNameEt.text.toString()
                task.task = bindingT.taskContentEt.text.toString()

                db.getDao().updateTask(task)
                val updatedList = db.getDao().getTasks()
                withContext(Dispatchers.Main) {
                    viewModel.setList(updatedList)
                }
            }
            editDialog.dismiss()
        }
        editDialog.show()

    }

}