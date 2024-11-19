package com.example.dayplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: MutableList<Task>,
    private val onEditClicked: (Task, Int) -> Unit,
    private val onDeleteClicked: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTextView: TextView = itemView.findViewById(R.id.taskNameTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    //Create viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    //Bind data to each ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskNameTextView.text = task.name  //Set the task name

        holder.editButton.setOnClickListener {
            onEditClicked(task, position)  //Handle the edit button click
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClicked(position)  //Handle the delete button click
        }
    }

    //Return the number of tasks
    override fun getItemCount(): Int {
        return tasks.size
    }

    //Update task
    fun updateTask(position: Int, newTask: Task) {
        tasks[position] = newTask
        notifyItemChanged(position)  //notify
    }

    //Remove task
    fun deleteTask(position: Int) {
        tasks.removeAt(position)
        notifyItemRemoved(position)  //notify
    }
}
