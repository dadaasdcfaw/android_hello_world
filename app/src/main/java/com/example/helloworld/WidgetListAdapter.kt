package com.example.helloworld

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class WidgetListAdapter(
    private val context: Context,
    private val widgetData: List<Pair<String, Int>>
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

        return view
    }
}