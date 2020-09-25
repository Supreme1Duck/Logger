package com.example.logger

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.logger.MyLoggerClasses.BroadCastReciever.MyBroadCastReciever
import com.example.logger.MyLoggerClasses.Service.MainService

class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var myBroadCastReciever: MyBroadCastReciever
    private lateinit var switch: SwitchCompat
    private var isSwitched: Int = 1
    private val isSwichedTag = "IsSwitched"
    private lateinit var prefs: SharedPreferences
    private val editorTag = "Shared_Preferences"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switch = findViewById(R.id.switch1)
        switch.setOnCheckedChangeListener(this)
        registerScreenStatusReceiver()
        prefs = getSharedPreferences("settings", MODE_PRIVATE)
    }

    private fun registerScreenStatusReceiver() {
        myBroadCastReciever = MyBroadCastReciever()
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(myBroadCastReciever, filter)
    }

    override fun onStop() {
        super.onStop()
        val editor = prefs.edit()
        editor.putInt(editorTag, isSwitched).apply()
    }

    override fun onResume() {
        super.onResume()
        isSwitched = prefs.getInt(editorTag, 0)
        postInIntentIsSwitched()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadCastReciever)
    }

    fun getSwitch(): Int {
        return isSwitched
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        if (p1) {
            this.isSwitched = 2
        } else {
            this.isSwitched = 1
        }
        postInIntentIsSwitched()
    }

    private fun postInIntentIsSwitched() {
        val intent = Intent(this, MainService::class.java)
        intent.putExtra(isSwichedTag, isSwitched)
        startService(intent)
    }
}