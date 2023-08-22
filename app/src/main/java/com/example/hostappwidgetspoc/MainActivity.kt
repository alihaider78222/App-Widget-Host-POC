package com.example.hostappwidgetspoc

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.hostappwidgetspoc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val WIDGET_HOST_ID = 123
    private var appWidgetManager: AppWidgetManager? = null
    private var appWidgetHost: AppWidgetHost? = null
    private var packageManager: PackageManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

//        appWidgetHost = AppWidgetHost(this, ExploreByTouchHelper.HOST_ID)
//        appWidgetManager = AppWidgetManager.getInstance(this)
//        val installedPackages = appWidgetManager?.getInstalledProviders()
//        println("Installed Packages are : $installedPackages");





        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        appWidgetManager = AppWidgetManager.getInstance(this)
        appWidgetHost = AppWidgetHost(this, WIDGET_HOST_ID)
        packageManager = getPackageManager()

        // Populate app icons

        // Populate app icons
        val appList: List<ResolveInfo> = getInstalledApps() as List<ResolveInfo>
        for (appInfo in appList) {
            val appIcon = ImageView(this)
            appIcon.setImageDrawable(appInfo.loadIcon(packageManager))
            appIcon.setOnClickListener { view: View? ->
                launchApp(
                    appInfo
                )
            }
            binding.gridLayout.addView(appIcon)
        }


//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAnchorView(R.id.fab)
//                .setAction("Action", null).show()
//        }
    }

    private fun getInstalledApps(): List<ResolveInfo?>? {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        return packageManager!!.queryIntentActivities(intent, 0)
    }

    private fun launchApp(appInfo: ResolveInfo) {

        var packageName = appInfo.activityInfo.packageName

        println("packageName is : $packageName")


//        if (packageName.equals("com.android.deskclock.AnalogAppWidgetProvider")) {
//        if (packageName.equals("com.android.settings")) {
//            val appWidgetId = appWidgetHost!!.allocateAppWidgetId()
//            println("appWidgetId is : $appWidgetId");

//            val widgetInfo = getWidgetInfo() // for clock
            val widgetInfo = getSettingsWidgetInfo() // for settings

            if (widgetInfo != null) {
                println("widgetInfo is : $widgetInfo");

                val appWidgetId = appWidgetHost!!.allocateAppWidgetId()
                println("appWidgetId is : $appWidgetId");
//                val widgetIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_BIND)
//                widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
//                widgetIntent.putExtra(
//                    AppWidgetManager.EXTRA_APPWIDGET_PROVIDER,
//                    widgetInfo.provider
//                )
//                startActivityForResult(widgetIntent, 1)

                val widgetView = appWidgetHost!!.createView(
                    this, appWidgetId, widgetInfo
                )
                println("widgetView is : $widgetView");
                binding.gridLayout.addView(widgetView)
                binding.gridLayout.requestLayout();
                binding.gridLayout.invalidate();
            } else {
                println("widgetInfo is NULL");
            }






//        }

//        val launchIntent = packageManager!!.getLaunchIntentForPackage(packageName)
//        launchIntent?.let { startActivity(it) }
    }

    private fun getSettingsWidgetInfo(): AppWidgetProviderInfo? {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetInfos = appWidgetManager.installedProviders
        println("widgetInfos are : $widgetInfos");
        for (info in widgetInfos) {
            println("info.provider.className :  ${info.provider.className}");

//            if (info.provider.className == "com.transsion.alarmclock.AnalogAppWidgetProvider") // For Small Devices
            if (info.provider.className == "com.sec.android.widgetapp.analogclock.AnalogClockWidgetProvider") // For High devices
            {
                return info

            }
        }
        return null // Widget not found
    }

//    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            val appWidgetId = data.getIntExtra(
//                AppWidgetManager.EXTRA_APPWIDGET_ID,
//                AppWidgetManager.INVALID_APPWIDGET_ID
//            )
//            if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
//                val widgetView = appWidgetHost!!.createView(this, appWidgetId, getSettingsWidgetInfo())
//                binding.gridLayout.addView(widgetView)
//            }
//        }
//    }

    private fun getWidgetInfo(): AppWidgetProviderInfo? {
        // Replace with the actual Clock widget provider info retrieval logic
        // For demonstration, we'll use a placeholder widget provider info
        return AppWidgetProviderInfo()
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}