package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.util.*


abstract class JsonConverter<T>(private val jsonFilename: String) {
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val type =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]

    // conversion from object to json
    fun toJson(element: T?) {
        try {
            FileWriter(jsonFilename).use { fileWriter ->
                if (element == null) {
                    throw NullPointerException("ELEMENT IS NULL")
                }
                gson.toJson(element, fileWriter)
            }
        } catch (e: NullPointerException) {
            System.err.println(e.message)
        } catch (e: IOException) {
            System.err.println("TO JSON - JSON FILENAME EXCEPTION")
        }
    }

    // conversion from json to object
    @RequiresApi(Build.VERSION_CODES.N)
    fun fromJson(): Optional<T> {
        try {
            FileReader(jsonFilename).use { fileReader ->
                return Optional.of(
                    gson.fromJson(
                        fileReader,
                        type
                    )
                )
            }
        } catch (e: IOException) {
            System.err.println("FROM JSON - JSON FILENAME EXCEPTION")
        }
        return Optional.empty()
    }

}