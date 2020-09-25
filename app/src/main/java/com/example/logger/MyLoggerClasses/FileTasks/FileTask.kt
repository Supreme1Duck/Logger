package com.example.logger.MyLoggerClasses.FileTasks

import android.content.Context
import android.content.Context.MODE_APPEND
import android.os.Environment
import android.widget.Toast
import java.io.*

class FileTask {
    private val sdFileName = "MyFile"
    private val sdDir = "MyFiles"
    private val fileName = "MyInternalLoggerFile"
    private lateinit var internalFile: Writer
    private lateinit var externalFile: BufferedWriter
    private lateinit var sdFile: File

    fun createOrOpenFile(context: Context) {
        internalFile =
            BufferedWriter(OutputStreamWriter(context.openFileOutput(fileName, MODE_APPEND)))
    }

    fun writeInFile(message: String, context: Context) {
        internalFile =
            BufferedWriter(OutputStreamWriter(context.openFileOutput(fileName, MODE_APPEND)))
        internalFile.write(message)
        internalFile.close()
    }

    fun createOrOpenExternalFile(context: Context) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD CARD is not available!", Toast.LENGTH_SHORT).show()
            return
        } else {

            var sdPath = context.getExternalFilesDir(null)
            sdPath = File(sdPath!!.absolutePath + "/" + sdDir)
            sdPath.mkdirs()
            sdFile = File(sdPath, sdFileName)
            externalFile = BufferedWriter(FileWriter(sdFile, true))
        }
    }

    fun writeInExternalFile(message: String) {
        externalFile.write(message)
    }

    fun closeThelWriter() {
        externalFile.close()
        internalFile.close()
    }
}