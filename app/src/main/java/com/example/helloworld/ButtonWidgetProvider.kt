package com.example.helloworld
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.os.Handler
import android.os.Looper

class ButtonWidgetProvider : AppWidgetProvider() {
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d("???", "onEnabled")
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
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            // Get the layout for the App Widget
            val views = RemoteViews(context.packageName, R.layout.widget_button)

            // Set the click listener to the button
            views.setOnClickPendingIntent(R.id.button_image, pendingIntent)

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        // Check if the custom action is received
        if (intent.action == "CLICK_ACTION") {
            // Restore original image when widget is not being clicked
            val views = RemoteViews(context.packageName, R.layout.widget_button)
            views.setImageViewResource(R.id.button_image, R.drawable.boton_pulsado) // Replace with your original image
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, ButtonWidgetProvider::class.java)
            appWidgetManager.updateAppWidget(thisWidget, views)
            // Schedule a timer to revert the image after a certain period of time
            Handler(Looper.getMainLooper()).postDelayed({
                // Revert back to the original image
                views.setImageViewResource(R.id.button_image, R.drawable.boton) // Change to the original image
                appWidgetManager.updateAppWidget(thisWidget, views)
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