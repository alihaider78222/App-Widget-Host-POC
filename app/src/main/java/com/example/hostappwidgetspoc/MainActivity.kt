package com.example.hostappwidgetspoc

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.customview.widget.ExploreByTouchHelper
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




    //   appWidgetHost = AppWidgetHost(this, ExploreByTouchHelper.HOST_ID)
//        appWidgetManager = AppWidgetManager.getInstance(this)
//        val installedPackages = appWidgetManager?.getInstalledProviders()
//        println("Installed Packages are : $installedPackages");





        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        appWidgetManager = AppWidgetManager.getInstance(this)
        appWidgetHost = AppWidgetHost(this, ExploreByTouchHelper.HOST_ID)
        packageManager = getPackageManager()

        // Populate app icons

        // Populate app icons

        getSettingsWidgetInfo()
        /* val appList: List<ResolveInfo> = getInstalledApps() as List<ResolveInfo>
         for (appInfo in appList) {
             val appIcon = ImageView(this)
             appIcon.setImageDrawable(appInfo.loadIcon(packageManager))
             appIcon.setOnClickListener { view: View? ->
                 launchApp(
                     appInfo
                 )
             }
             binding.gridLayout.addView(appIcon)
         }*/


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
               /* println("widgetInfo is : $widgetInfo");

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

                widgetView.setBackgroundColor(resources.getColor(R.color.black))

                println("widgetView is : $widgetView");
                binding.gridLayout.requestLayout()
                binding.gridLayout.invalidate()*/
            } else {
                println("widgetInfo is NULL");
            }






//        }

//        val launchIntent = packageManager!!.getLaunchIntentForPackage(packageName)
//        launchIntent?.let { startActivity(it) }
    }



    @RequiresApi(Build.VERSION_CODES.S)
    private fun getSettingsWidgetInfo(): AppWidgetProviderInfo? {
        val sTmpRect = Rect()
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetInfos = appWidgetManager.installedProviders
        println("widgetInfos are : $widgetInfos");
        for (info in widgetInfos) {



           // info.maxResizeWidth = 50
            println("info.provider.className :  ${info.provider}");


            println("widgetInfo is : $info.");


            var options  = Bundle()
            options.putInt(
                AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH,
                sTmpRect.left - 20
            )

            options.putInt(
                AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT,
             sTmpRect.top - 20
            )
            options.putInt(
                AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH,
               sTmpRect.right - 20
            )
            options.putInt(
                AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, sTmpRect.bottom - 20
            )



            val appWidgetId = appWidgetHost!!.allocateAppWidgetId()
            val success  = appWidgetManager.bindAppWidgetIdIfAllowed(appWidgetId,info.provider,options)


           // val widgetOption = appWidgetManager.getAppWidgetOptions(appWidgetId)


            if(info.provider.className=="com.google.android.calendar.widgetmonth.MonthViewWidgetProvider"){



                val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_BIND).apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER, info.provider)
                    // This is the options bundle described in the preceding section.
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_OPTIONS, options)
                }
                startActivityForResult(intent, 123)



                val widgetHost = appWidgetHost!!.createView(
                    this, appWidgetId, info
                )

                widgetHost.setAppWidget(appWidgetId,info)
                widgetHost.setBackgroundColor(resources.getColor(R.color.red))


                binding.mainLayout.addView(widgetHost)
                binding.mainLayout.requestLayout()
                binding.mainLayout.invalidate()

            }





            val widgetView  = LinearLayout(this)

             widgetView.layoutParams  = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            widgetView.orientation = LinearLayout.VERTICAL
            val name  =  TextView(this)
            name.text  = info.loadLabel(packageManager)
            name.setTextColor(resources.getColor(R.color.black))
            name.textSize = 40f
            widgetView.addView(name)


            val preview = ImageView(this)
            val previewParams  = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)
            preview.scaleType = ImageView.ScaleType.CENTER_CROP
            preview.layoutParams  = previewParams
            preview.setImageDrawable(info.loadPreviewImage(this,400))



            widgetView.addView(preview)

            val header  = TextView(this)
            header.text = info.loadLabel(packageManager)
            header.textSize  = 25.0f
            header.setTextColor(resources.getColor(R.color.black))
            val linearLayout =  LinearLayout(this)
            linearLayout.orientation  = LinearLayout.VERTICAL
            linearLayout.layoutParams  = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT)

            linearLayout.addView(header)
          //  linearLayout.addView(widgetHost)



        //   Log.i("APP WIDGET INFO OF ${info.provider.packageName} ", " ${ widgetView.appWidgetInfo}")





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