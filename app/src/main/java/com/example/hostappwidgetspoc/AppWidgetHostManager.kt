package com.example.hostappwidgetspoc

import android.app.Activity
import android.appwidget.AppWidgetHost

class AppWidgetHostManager(context:Activity) {
    var mAppWidgetHost: AppWidgetHost? = null

    init {
        mAppWidgetHost = AppWidgetHost(context,R.id.APPWIDGET_HOST_ID)
    }


    companion object : SingletonHolder<AppWidgetHostManager, Activity>(::AppWidgetHostManager)
}