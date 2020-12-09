package com.example.myapplication.exercises

import android.net.Uri
import com.example.myapplication.R
import java.io.Serializable

class Part : Serializable {
    private lateinit var name: String
    private lateinit var img: String

    companion object Factory {
        private var map: HashMap<String, Part> = HashMap()

        init {
            map.put("chest", Part().setName("chest"))
            map.put("back", Part().setName("back"))
            map.put("shoulders", Part().setName("shoulders"))
            map.put("cardio", Part().setName("cardio"))
            map.put("triceps", Part().setName("triceps"))
            map.put("biceps", Part().setName("biceps"))

            map.put("legs", Part().setName("legs"))
            map.put("stomach", Part().setName("stomach"))

        }

        fun getPart(name: String): Part? {
            return map.get(name.toLowerCase())
        }
    }

    private fun setName(name: String): Part {
        this.name = name.toLowerCase()
        when (this.name) {
            "chest" -> img="android.resource://com.example.myapplication/" + R.drawable.chest
            "back" -> img="android.resource://com.example.myapplication/" + R.drawable.back
            "shoulders" -> img="android.resource://com.example.myapplication/"+ R.drawable.shoulders
            "cardio" -> img="android.resource://com.example.myapplication/" + R.drawable.cardio
            "triceps" -> img="android.resource://com.example.myapplication/" + R.drawable.triceps
            "biceps" -> img="android.resource://com.example.myapplication/" + R.drawable.biceps
            "legs" -> img="android.resource://com.example.myapplication/" + R.drawable.legs
            "stomach" -> img="android.resource://com.example.myapplication/" + R.drawable.stomach
        }
        return this
    }

    fun getName(): String {
        return name
    }

    fun getImg(): String {
        return img
    }

    override fun toString(): String {
        return name[0].toUpperCase().toString() + name.subSequence(1, name.length)
    }
}