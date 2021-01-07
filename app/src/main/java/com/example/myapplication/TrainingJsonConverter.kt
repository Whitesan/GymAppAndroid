package com.example.myapplication

import android.util.Log
import com.example.myapplication.exercises.Trainings
import com.google.gson.Gson
import java.io.*


class TrainingJsonConverter {
    companion object{
         fun loadTrainingJson(yourFilePath:String): Trainings {
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
        // val yourFilePath = CreateTrainingActivity.filesDir.toString() + "/" +"Trainimg.json"
        val yourFile = File(yourFilePath)
//Create your FileOutputStream, yourFile is part of the constructor
        val fileOutputStream = FileOutputStream(yourFile)
//Convert your JSON String to Bytes and write() it
        fileOutputStream.write(yourObjectJson.toByteArray())
//Finally flush and close your FileOutputStream
        fileOutputStream.flush()
        fileOutputStream.close()
    }

    fun fromJson(yourFilePath: String): Trainings? {
        val gson = Gson()
        var text = ""
//Make sure to use a try-catch statement to catch any errors
        try {
            //Make your FilePath and File

            val yourFile = File(yourFilePath)
            //Make an InputStream with your File in the constructor
            val inputStream: InputStream = FileInputStream(yourFile)
            val stringBuilder = StringBuilder()
            //Check to see if your inputStream is null
            //If it isn't use the inputStream to make a InputStreamReader
            //Use that to make a BufferedReader
            //Also create an empty String
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                //Use a while loop to append the lines from the Buffered reader
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append(receiveString)
                }
                //Close your InputStream and save stringBuilder as a String
                inputStream.close()
                text = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            //Log your error with Log.e
        } catch (e: IOException) {
            //Log your error with Log.e
        }
//Use Gson to recreate your Object from the text String
        return gson.fromJson(text, Trainings::class.java)
    }

}


