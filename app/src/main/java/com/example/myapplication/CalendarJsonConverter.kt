package com.example.myapplication

import android.util.Log
import com.example.myapplication.exercises.Calendar
import com.example.myapplication.exercises.Trainings
import com.google.gson.Gson
import java.io.*


class CalendarJsonConverter {
    companion object {
        fun loadCalendarJson(yourFilePath: String): Calendar {
            val json: CalendarJsonConverter = CalendarJsonConverter()
            var calendar: Calendar? = json.fromJson(yourFilePath)
            if (calendar == null)
            {
                calendar = Calendar()
                calendar.init()
            }
            //else
               // Log.i("loadTrainingJson", trainings.showTrainings())
            return calendar
        }
    }
    fun toJson(calendar: Calendar, yourFilePath: String) {
        val gson = Gson()
        val yourObjectJson = gson.toJson(calendar)
        val yourFile = File(yourFilePath)
        val fileOutputStream = FileOutputStream(yourFile)
        fileOutputStream.write(yourObjectJson.toByteArray())
        fileOutputStream.flush()
        fileOutputStream.close()
    }

    fun fromJson(yourFilePath: String): Calendar? {
        val gson = Gson()
        var text = ""
        try {
            val yourFile = File(yourFilePath)
            val inputStream: InputStream = FileInputStream(yourFile)
            val stringBuilder = StringBuilder()
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var receiveString: String?
            while (bufferedReader.readLine().also { receiveString = it } != null) {
                stringBuilder.append(receiveString)
            }
            inputStream.close()
            text = stringBuilder.toString()
        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
        }
        return gson.fromJson(text, Calendar::class.java)
    }

}