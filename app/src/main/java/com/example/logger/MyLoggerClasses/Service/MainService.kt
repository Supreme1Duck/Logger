package com.example.logger.MyLoggerClasses.Service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.logger.MyLoggerClasses.FileTasks.FileTask

class MainService : Service() {
    private val recievingActionFromService = "OUR ACTION"
    private val myOwnTag = "MY_OWN_TAG"
    private var actionFromService = ""
    private var fileTask = FileTask()
    private var isSwitched: Int = 1
    private val isSwitchedTag = "IsSwitched"

    override fun onCreate() {
        fileTask.createOrOpenExternalFile(context = baseContext)
        fileTask.createOrOpenFile(context = baseContext)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.getStringExtra(myOwnTag) != null) {
            val date = intent.getStringExtra(myOwnTag)
            actionFromService = intent.getStringExtra(recievingActionFromService)!!
            if (isSwitched == 1)
                Thread {
                    fileTask.writeInFile(
                        actionFromService + date!!,
                        context = baseContext
                    )
                }.start()
            if (isSwitched == 2)
                Thread({ fileTask.writeInExternalFile(actionFromService + date!!) })
        } else {
            isSwitched = intent!!.getIntExtra(isSwitchedTag, 0)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        fileTask.closeThelWriter()
    }
}