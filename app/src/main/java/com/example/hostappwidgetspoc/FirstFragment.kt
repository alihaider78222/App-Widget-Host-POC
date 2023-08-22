package com.example.hostappwidgetspoc

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.customview.widget.ExploreByTouchHelper.HOST_ID
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hostappwidgetspoc.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    fun addAppWidget(data: Intent) {
//        val appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
//        val customWidget = data.getStringExtra(EXTRA_CUSTOM_WIDGET)
//        val appWidget: AppWidgetProviderInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId)
//        if (appWidget.configure != null) {
//            // Launch over to configure widget, if needed.
//            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE)
//            intent.component = appWidget.configure
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
//            startActivityForResult(intent, REQUEST_CREATE_APPWIDGET)
//        } else {
//            // Otherwise, finish adding the widget.
//        }
//    }
}