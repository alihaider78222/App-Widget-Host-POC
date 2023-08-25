package com.example.hostappwidgetspoc

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.hostappwidgetspoc.databinding.ActivityWidgetListScreenBinding
import com.example.hostappwidgetspoc.databinding.WidgetItemLayoutBinding

class WidgetListScreen : AppCompatActivity() {
      lateinit  var widgetListScreenBinding:ActivityWidgetListScreenBinding
      lateinit  var widgetItemBinding:WidgetItemLayoutBinding
      lateinit var mAppWidgetManager: AppWidgetManager
       lateinit var mAppWidgetHost: AppWidgetHost
      var widgetId:Int?=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        widgetListScreenBinding = DataBindingUtil.setContentView(this,R.layout.activity_widget_list_screen)
        mAppWidgetManager   = AppWidgetManager.getInstance(this)
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,-1)
        mAppWidgetHost = AppWidgetHostManager.getInstance(this).mAppWidgetHost!!


        getWidgetList()

    }


    fun getWidgetList(){
        val providers = mAppWidgetManager.installedProviders
        if(!providers.isNullOrEmpty()){
            for(provider in providers){
                val widgetName = provider.loadLabel(packageManager)
                val widgetIcon =  provider.loadIcon(this,300)
                val widgetPreview = provider.loadPreviewImage(this,300)
                if(widgetPreview!=null && widgetIcon!=null && widgetName!=null){
                    createWidgetView(widgetName,widgetIcon,widgetPreview,provider)
                }

            }
        }
    }


    fun createWidgetView(widgetName:String,widgetIcon:Drawable,widgetPreView:Drawable,provider:AppWidgetProviderInfo){
        widgetItemBinding  = WidgetItemLayoutBinding.inflate(LayoutInflater.from(this))
        widgetItemBinding.lable.text = widgetName
        widgetItemBinding.widgetIcon.setImageDrawable(widgetIcon)
        widgetItemBinding.previewImage.setImageDrawable(widgetPreView)
        widgetListScreenBinding.widgetListVIew.addView(widgetItemBinding.root)
        widgetListScreenBinding.widgetListVIew.requestLayout()
        widgetListScreenBinding.widgetListVIew.invalidate()

        widgetItemBinding.root.setOnClickListener{
            widgetId?.let {
              var success  =  mAppWidgetManager.bindAppWidgetIdIfAllowed(it,provider.provider)

                if(success){
                  val intent  = Intent()
                  intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId)
                  setResult(RESULT_OK,intent)
                    finish()

                }

            }

        }



    }
}