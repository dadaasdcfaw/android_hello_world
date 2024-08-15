package com.example.helloworld

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.helloworld.interfaces.OnRenameClickListener
import com.example.helloworld.interfaces.OnResetClickListener

class WidgetListAdapter(
    private val context: Context,
    private val widgetData: MutableList<Pair<String, Int>>,
    private val onResetClickListener: OnResetClickListener,
    private val onRenameClickListener: OnRenameClickListener
) : BaseAdapter() {

    override fun getCount(): Int = widgetData.size

    override fun getItem(position: Int): Any = widgetData[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.widget_list_item, parent, false)
        val nameTextView: TextView = view.findViewById(R.id.widget_name)
        val countTextView: TextView = view.findViewById(R.id.widget_count)

        val (name, count) = getItem(position) as Pair<String, Int>
        nameTextView.text = name
        countTextView.text = count.toString()

        // Set listeners using the provided interfaces
        val resetButton: ImageButton = view.findViewById(R.id.button_reset)
        resetButton.setOnClickListener {
            onResetClickListener.onResetClick(position)
        }

        val renameButton: ImageButton = view.findViewById(R.id.button_rename)
        renameButton.setOnClickListener {
            onRenameClickListener.onRenameClick(position)
        }
        return view
    }
    // Method to update an item
    fun updateItem(position: Int, newName: String?=null, newCount: Int?=null) {
        if (position in widgetData.indices) {
            val (currentName, currentCount) = widgetData[position]
            val name = newName ?: currentName
            val count = newCount ?: currentCount
            widgetData[position] = Pair(name, count)
            notifyDataSetChanged() // Notify the adapter to refresh the view
        }
    }
}