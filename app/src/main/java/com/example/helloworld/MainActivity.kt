package com.example.helloworld

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun updateWidgetData(): List<Pair<String, Int>> {
        val componentName = ComponentName(this, ButtonWidgetProvider::class.java)

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetIds = appWidgetManager.getAppWidgetIds(componentName)

        val widgetData = mutableListOf<Pair<String, Int>>()

        for (widgetId in widgetIds) {
            val name = ButtonWidgetProvider.getWidgetName(this, widgetId)  // **UPDATED**
            val count = ButtonWidgetProvider.getWidgetCount(this, widgetId)  // **UPDATED**
            widgetData.add(name to count)
        }
        return widgetData
    }
    override fun onResume() {
        super.onResume()
        val widgetListView: ListView = findViewById(R.id.widget_list_view)

        val widgetData = updateWidgetData()
        val adapter = WidgetListAdapter(this, widgetData)
        widgetListView.adapter = adapter
    }
}
