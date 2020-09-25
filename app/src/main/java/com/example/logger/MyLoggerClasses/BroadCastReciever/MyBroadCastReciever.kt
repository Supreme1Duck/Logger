package com.example.logger.MyLoggerClasses.BroadCastReciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.logger.MyLoggerClasses.Service.MainService
import java.text.SimpleDateFormat
import java.util.*

class MyBroadCastReciever : BroadcastReceiver() {
    private val sendingToServiceTag = "MY_OWN_TAG"
    private val sendingActionToService = "OUR ACTION"
    override fun onReceive(context: Context?, p1: Intent?) {
        if (p1 != null) {
            var currentDate = Date()
            var dateFormat = SimpleDateFormat("yyyy:MM:dd:HH:mm", Locale.getDefault())
            var date_Time: String? = null
            when (p1.action) {
                Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                    date_Time = dateFormat.format(currentDate)
                    val intent = Intent(context, MainService::class.java)
                    intent.putExtra(sendingToServiceTag, date_Time)
                    intent.putExtra(sendingActionToService, "AIRPLANE MODE CHANGED :")
                    context?.startService(intent)
                }

                Intent.ACTION_SCREEN_ON -> {
                    date_Time = dateFormat.format(currentDate)
                    val intent = Intent(context, MainService::class.java)
                    intent.putExtra(sendingToServiceTag, date_Time)
                    intent.putExtra(sendingActionToService, "SCREEN IS ON :")
                    context?.startService(intent)
                }

                Intent.ACTION_SCREEN_OFF -> {
                    date_Time = dateFormat.format(currentDate)
                    val intent = Intent(context, MainService::class.java)
                    intent.putExtra(sendingToServiceTag, date_Time)
                    intent.putExtra(sendingActionToService, "SCREEN IS OFF :")
                    context?.startService(intent)
                }

            }
        }
    }
}