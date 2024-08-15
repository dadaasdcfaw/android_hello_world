package com.example.helloworld

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.helloworld.interfaces.OnRenameClickListener
import com.example.helloworld.interfaces.OnResetClickListener

class MainActivity : AppCompatActivity(), OnResetClickListener, OnRenameClickListener {

    private lateinit var adapter: WidgetListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun updateWidgetData(): MutableList<Pair<String, Int>> {
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
        val adapter = WidgetListAdapter(this, widgetData, this, this)
        widgetListView.adapter = adapter
    }

    override fun onResetClick(position: Int) {
        val componentName = ComponentName(this, ButtonWidgetProvider::class.java)

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetIds = appWidgetManager.getAppWidgetIds(componentName)
        ButtonWidgetProvider.saveWidgetCount(this, widgetIds[position], 0)

        val widgetListView: ListView = findViewById(R.id.widget_list_view)
        val widgetData = updateWidgetData()
        val adapter = WidgetListAdapter(this, widgetData, this, this)
        widgetListView.adapter = adapter
        adapter.updateItem(position, newCount = 0)
    }

    override fun onRenameClick(position: Int) {
        TODO("Not yet implemented")
    }
}
