package com.example.myapplication

import com.example.myapplication.exercises.Training
import com.google.gson.Gson
import java.io.*


class TrainingJsonConverter()
{
    fun toJson(training: Training, yourFilePath: String)
    {
        val gson = Gson()
        val yourObjectJson = gson.toJson(training)
       // val yourFilePath = CreateTrainingActivity.filesDir.toString() + "/" +"Trainimg.json"
        val yourFile = File(yourFilePath)
//Create your FileOutputStream, yourFile is part of the constructor
//Create your FileOutputStream, yourFile is part of the constructor
        val fileOutputStream = FileOutputStream(yourFile)
//Convert your JSON String to Bytes and write() it
//Convert your JSON String to Bytes and write() it
        fileOutputStream.write(yourObjectJson.toByteArray())
//Finally flush and close your FileOutputStream
//Finally flush and close your FileOutputStream
        fileOutputStream.flush()
        fileOutputStream.close()
    }
    fun fromJson(yourFilePath: String):Training
    {
        val gson = Gson()
        var text = ""
//Make sure to use a try-catch statement to catch any errors
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
//Use Gson to recreate your Object from the text String
        var yourObject: Training = gson.fromJson(text,Training::class.java)
        return yourObject
    }

}


