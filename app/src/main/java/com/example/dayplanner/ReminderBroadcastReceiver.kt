package com.example.dayplanner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val taskName = intent?.getStringExtra("task_name") ?: "Task"

        val builder = NotificationCompat.Builder(context!!, "TASK_REMINDER_CHANNEL")
            .setSmallIcon(R.drawable.reminder)  // Use your app icon here
            .setContentTitle("Task Reminder")
            .setContentText("Reminder for task: $taskName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, builder.build())

        Toast.makeText(context, "Reminder for task: $taskName", Toast.LENGTH_SHORT).show()
    }
}
