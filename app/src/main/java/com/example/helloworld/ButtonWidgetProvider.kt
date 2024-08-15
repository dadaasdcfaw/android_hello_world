package com.example.helloworld
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RemoteViews

class ButtonWidgetProvider : AppWidgetProvider() {
    companion object {
        const val PREFS_NAME = "com.example.helloworld.ButtonWidgetProvider"
        const val PREF_NAME_KEY = "name_"
        const val PREF_COUNT_KEY = "count_"

        // **NEW** Method to retrieve widget name
        fun getWidgetName(context: Context, appWidgetId: Int): String {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

            val default_value = R.string.default_widget_text.toString()
            return prefs.getString(PREF_NAME_KEY + appWidgetId, default_value) ?: default_value
        }

        fun getWidgetCount(context: Context, appWidgetId: Int): Int {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return prefs.getInt(PREF_COUNT_KEY + appWidgetId, 0)
        }
        fun saveWidgetCount(context: Context, appWidgetId: Int, count: Int) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit()
                .putInt(PREF_COUNT_KEY  + appWidgetId, count)
                .apply()


            // Get the AppWidgetManager
            val appWidgetManager = AppWidgetManager.getInstance(context)

            // Get the layout for the App Widget
            val views = RemoteViews(context.packageName, R.layout.widget_button)
            views.setTextViewText(R.id.tap_counter, count.toString())

            // Update the widget with the new RemoteViews
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Iterate through each widget added
        for (appWidgetId in appWidgetIds) {
            // Create an Intent to update the widget when clicked
            val intent = Intent(context, ButtonWidgetProvider::class.java)
            intent.action = "CLICK_ACTION"
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            val pendingIntent = PendingIntent.getBroadcast(
                /* context = */ context,
                /* requestCode = */ appWidgetId,
                /* intent = */ intent,
                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            // Get the layout for the App Widget
            val views = RemoteViews(context.packageName, R.layout.widget_button)

            // Set the click listener to the button
            views.setOnClickPendingIntent(R.id.button_image, pendingIntent)

            // Get current widget name stored in preferences.
            val currWidgetName = getWidgetName(context, appWidgetId)
            if (currWidgetName != R.string.default_widget_text.toString()) {
                // And update it if different than default
                views.setTextViewText(appWidgetId, currWidgetName)
            }

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        // Check if the custom action is received
        if (intent.action == "CLICK_ACTION") {
            val widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)

            // Restore original image when widget is not being clicked
            val views = RemoteViews(context.packageName, R.layout.widget_button)
            views.setImageViewResource(R.id.button_image, R.drawable.boton_pulsado) // Replace with your original image
            val appWidgetManager = AppWidgetManager.getInstance(context)
            appWidgetManager.updateAppWidget(widgetId, views)

            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val currentCount = prefs.getInt(PREF_COUNT_KEY + widgetId, 0)


            // Increment the count by 1
            val newCount = currentCount + 1
            // Store the new count back to SharedPreferences
            saveWidgetCount(context, widgetId, newCount)


            views.setTextViewText(R.id.tap_counter, newCount.toString())
            Log.d("ButtonWidgetProvider", "Updating widget ID $widgetId with count $currentCount")

            // Schedule a timer to revert the image after a certain period of time
            Handler(Looper.getMainLooper()).postDelayed({
                // Revert back to the original image
                views.setImageViewResource(R.id.button_image, R.drawable.boton) // Change to the original image
                appWidgetManager.updateAppWidget(widgetId, views)
            }, 100) // Change 3000 to the desired duration in milliseconds (e.g., 3 seconds)
    }
    }
    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d("???", "onDeleted $appWidgetIds")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d("???", "onDisabled")
    }

}