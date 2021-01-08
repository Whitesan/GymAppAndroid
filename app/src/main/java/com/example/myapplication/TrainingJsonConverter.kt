package com.example.myapplication

import android.util.Log
import com.example.myapplication.exercises.Trainings
import com.google.gson.Gson
import java.io.*


class TrainingJsonConverter {
    companion object {
        fun loadTrainingJson(yourFilePath: String): Trainings {
            val json: TrainingJsonConverter = TrainingJsonConverter()
            var trainings: Trainings? = json.fromJson(yourFilePath)
            if (trainings == null)
                trainings = Trainings(ArrayList())
            else
                Log.i("loadTrainingJson", trainings.showTrainings())
            return trainings
        }
    }
    fun toJson(training: Trainings, yourFilePath: String) {
        val gson = Gson()
        val yourObjectJson = gson.toJson(training)
        val yourFile = File(yourFilePath)
        val fileOutputStream = FileOutputStream(yourFile)
        fileOutputStream.write(yourObjectJson.toByteArray())
        fileOutputStream.flush()
        fileOutputStream.close()
    }

    fun fromJson(yourFilePath: String): Trainings? {
        val gson = Gson()
        var text = ""
        try {
            val yourFile = File(yourFilePath)
            val inputStream: InputStream = FileInputStream(yourFile)
            val stringBuilder = StringBuilder()
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            var receiveString: String? = ""
            while (bufferedReader.readLine().also { receiveString = it } != null) {
                stringBuilder.append(receiveString)
            }
            inputStream.close()
            text = stringBuilder.toString()
        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
        }
        return gson.fromJson(text, Trainings::class.java)
    }

}


