package com.example.dayplanner

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.content.ContextCompat

class TaskWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {

            val tasks = getTasksFromSharedPreferences(context)


            val views = RemoteViews(context.packageName, R.layout.activity_widget_layout)
            views.setTextViewText(R.id.widgetTaskList, tasks)

             val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.widgetTaskList, pendingIntent)


            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun getTasksFromSharedPreferences(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("task_prefs", Context.MODE_PRIVATE)
        val tasks = sharedPreferences.getStringSet("tasks_list", setOf()) ?: setOf()
        return if (tasks.isNotEmpty()) {
            tasks.joinToString(separator = "\n") { task -> task }
        } else {
            "No upcoming tasks"
        }
    }
}
