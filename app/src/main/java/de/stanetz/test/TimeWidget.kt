package de.stanetz.test

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.StrictMode
import android.util.Log
import android.widget.RemoteViews
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


/**
 * Implementation of App Widget functionality.
 */
class TimeWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // TODO niels 30.03.21: Better use https://developer.android.com/topic/libraries/architecture/coroutines
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        val url = URL("http://www.google.de/")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val currentDate = Date(urlConnection.date)
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, currentDate)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    currentDate: Date
) {
    val widgetText = context.getString(R.string.appwidget_text)
    Log.i("PowerTestWidget", "updateAppWidget: $currentDate")
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.time_widget)
    views.setTextViewText(R.id.appwidget_text, "Now: $currentDate")

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}