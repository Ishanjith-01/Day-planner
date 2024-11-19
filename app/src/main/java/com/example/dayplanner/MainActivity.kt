package com.example.dayplanner

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val PREFS_NAME = "task_prefs"
    private val TASKS_KEY = "tasks_list"

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val taskList = mutableListOf<Task>()
    private var editTaskPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskNameEditText = findViewById<EditText>(R.id.taskName)
        val addTaskButton = findViewById<Button>(R.id.addTaskButton)
        val recyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView)

        val openStopwatchButton = findViewById<Button>(R.id.sw)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        loadTasks()

        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList,
            onEditClicked = { task, position ->
                taskNameEditText.setText(task.name)
                editTaskPosition = position
                addTaskButton.text = "Update Task"
            },
            onDeleteClicked = { position ->
                taskAdapter.deleteTask(position)
                saveTasks()
            }
        )
        recyclerView.adapter = taskAdapter

        addTaskButton.setOnClickListener {
            val taskName = taskNameEditText.text.toString()

            if (taskName.isNotEmpty()) {
                if (editTaskPosition == null) {
                    val newTask = Task(taskName)
                    taskList.add(newTask)
                    taskAdapter.notifyDataSetChanged()
                    saveTasks()
                    taskNameEditText.text.clear()
                } else {
                    val updatedTask = Task(taskName)
                    taskAdapter.updateTask(editTaskPosition!!, updatedTask)
                    taskList[editTaskPosition!!] = updatedTask
                    saveTasks()
                    taskNameEditText.text.clear()
                    addTaskButton.text = "Add Task"
                    editTaskPosition = null
                }
            } else {
                Toast.makeText(this, "Please enter a task name", Toast.LENGTH_SHORT).show()
            }
        }


        openStopwatchButton.setOnClickListener {
            val intent = Intent(this, activity_stopwatch::class.java)
            startActivity(intent)
        }
    }

    private fun saveTasks() {
        val editor = sharedPreferences.edit()
        val taskSet = taskList.map { it.name }.toSet()
        editor.putStringSet(TASKS_KEY, taskSet)
        editor.apply()
    }

    private fun loadTasks() {
        val savedTasks = sharedPreferences.getStringSet(TASKS_KEY, setOf())
        taskList.clear()
        if (savedTasks != null) {
            taskList.addAll(savedTasks.map { Task(it) })
        }
    }
}
