package com.example.hostappwidgetspoc

import android.app.Activity
import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetHostView
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

/**
 * This activity serves as an example of how to search, add and remove widgets
 * from a window.
 *
 * @author Leonardo Garcia Fischer (http://coderender.blogspot.com/)
 */
class WidgetHostExampleActivity : Activity() {
    var mAppWidgetManager: AppWidgetManager? = null
    lateinit var mAppWidgetHost: AppWidgetHost
    var mainlayout: ViewGroup? = null
    var addWidget: Button? = null
    var removeWidget: Button? = null

    /**
     * Called on the creation of the activity.
     */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        addWidget = findViewById<View>(R.id.add_widget_btn) as Button
        removeWidget = findViewById<View>(R.id.remove_widget_btn) as Button
        mainlayout = findViewById<View>(R.id.main_layout) as ViewGroup
        mAppWidgetManager = AppWidgetManager.getInstance(this)
        mAppWidgetHost = AppWidgetHostManager.getInstance(this).mAppWidgetHost!!
        addWidget!!.setOnClickListener { selectWidget() }
        removeWidget!!.setOnClickListener { removeWidgetMenuSelected() }
    }

    /**
     * Launches the menu to select the widget. The selected widget will be on
     * the result of the activity.
     */
    fun selectWidget() {
        val appWidgetId = mAppWidgetHost.allocateAppWidgetId()
        Log.e("WIDGET ID SELECT", "ID$appWidgetId")
        val pickIntent = Intent(this, WidgetListScreen::class.java)
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

        addEmptyData(pickIntent)
        startActivityForResult(pickIntent, R.id.REQUEST_PICK_APPWIDGET)
    }

    /**
     * This avoids a bug in the com.android.settings.AppWidgetPickActivity,
     * which is used to select widgets. This just adds empty extras to the
     * intent, avoiding the bug.
     *
     * See more: http://code.google.com/p/android/issues/detail?id=4272
     */
    fun addEmptyData(pickIntent: Intent) {
        val customInfo = ArrayList<AppWidgetProviderInfo>()
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo)
        val customExtras = ArrayList<Bundle>()
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras)
    }

    /**
     * If the user has selected an widget, the result will be in the 'data' when
     * this function is called.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == R.id.REQUEST_PICK_APPWIDGET) {
                configureWidget(data)
            } else if (requestCode == R.id.REQUEST_CREATE_APPWIDGET) {
                createWidget(data)
            }
        } else if (resultCode == RESULT_CANCELED && data != null) {
            val appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
            if (appWidgetId != -1) {
                mAppWidgetHost!!.deleteAppWidgetId(appWidgetId)
            }
        }
    }

    /**
     * Checks if the widget needs any configuration. If it needs, launches the
     * configuration activity.
     */
    private fun configureWidget(data: Intent) {
        val extras = data.extras
        val appWidgetId = extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
        val appWidgetInfo = mAppWidgetManager!!.getAppWidgetInfo(appWidgetId)
        Log.e("WIDGET ID CONFIGURE", "ID$appWidgetId")
        if (appWidgetInfo.configure != null) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE)
            intent.component = appWidgetInfo.configure
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            startActivityForResult(intent, R.id.REQUEST_CREATE_APPWIDGET)
        } else {
            createWidget(data)
        }
    }

    /**
     * Creates the widget and adds to our view layout.
     */
    fun createWidget(data: Intent) {
        val extras = data.extras
        val appWidgetId = extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
        val appWidgetInfo = mAppWidgetManager!!.getAppWidgetInfo(appWidgetId)
        val hostView = mAppWidgetHost!!.createView(this, appWidgetId, appWidgetInfo)
        hostView.setAppWidget(appWidgetId, appWidgetInfo)
        mainlayout!!.addView(hostView)
        Log.i(TAG, "The widget size is: " + appWidgetInfo.minWidth + "*" + appWidgetInfo.minHeight)
    }

    /**
     * Registers the AppWidgetHost to listen for updates to any widgets this app
     * has.
     */
    override fun onStart() {
        super.onStart()
        mAppWidgetHost!!.startListening()
    }

    /**
     * Stop listen for updates for our widgets (saving battery).
     */
    override fun onStop() {
        super.onStop()
        //mAppWidgetHost.stopListening();
    }

    /**
     * Removes the widget displayed by this AppWidgetHostView.
     */
    fun removeWidget(hostView: AppWidgetHostView) {
        mAppWidgetHost!!.deleteAppWidgetId(hostView.appWidgetId)
        mainlayout!!.removeView(hostView)
    }

    /**
     * Handles the menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(TAG, "Menu selected: " + item.title + " / " + item.itemId + " / " + R.id.addWidget)
        if (item.itemId == R.id.addWidget) {
            selectWidget()
            return true
        } else if (item.itemId == R.id.removeWidget) {
            removeWidgetMenuSelected()
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Handle the 'Remove Widget' menu.
     */
    fun removeWidgetMenuSelected() {
        val childCount = mainlayout!!.childCount
        if (childCount > 1) {
            val view = mainlayout!!.getChildAt(childCount - 1)
            if (view is AppWidgetHostView) {
                removeWidget(view)
                Toast.makeText(this, R.string.widget_removed_popup, Toast.LENGTH_SHORT).show()
                return
            }
        }
        Toast.makeText(this, R.string.no_widgets_popup, Toast.LENGTH_SHORT).show()
    }

    /**
     * Creates the menu with options to add and remove widgets.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.widget_menu, menu)
        return true
    }

    companion object {
        const val TAG = "WidgetHostExampleActivity"
    }
}