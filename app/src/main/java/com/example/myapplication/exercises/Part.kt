package com.example.myapplication.exercises

import com.example.myapplication.R
import java.io.Serializable

class Part : Serializable {
    private lateinit var name: String
    private var drawId: Int = 0

    companion object Factory {
        private var map: HashMap<String, Part> = HashMap()

        init {
            map.put("chest", Part().setName("chest"))
            map.put("back", Part().setName("back"))
            map.put("shoulders", Part().setName("shoulders"))
            map.put("cardio", Part().setName("cardio"))
            map.put("triceps", Part().setName("triceps"))
            map.put("biceps", Part().setName("biceps"))
            map.put("abs", Part().setName("abs"))
            map.put("calves", Part().setName("calves"))
            map.put("neck", Part().setName("neck"))
            map.put("thighs", Part().setName("thighs"))
            map.put("forearms", Part().setName("forearms"))


        }

        fun getPart(name: String): Part? {
            return map.get(name.toLowerCase())
        }
    }

    private fun setName(name: String): Part {
        this.name = name.toLowerCase()

        when (this.name) {
            "chest" -> drawId = R.drawable.chest
            "back" -> drawId = R.drawable.back
            "shoulders" -> drawId = R.drawable.shoulders
            "cardio" -> drawId = R.drawable.cardio
            "triceps" -> drawId = R.drawable.triceps
            "biceps" -> drawId = R.drawable.biceps
            "neck" -> drawId = R.drawable.neck
            "forearms" -> drawId = R.drawable.forearm
            "thighs" -> drawId = R.drawable.thighs
            "calves" -> drawId = R.drawable.calves
            "abs" -> drawId = R.drawable.abs
        }
        return this
    }

    fun getName(): String {
        return name
    }

    fun getImg(): Int {
        return drawId
    }

    override fun toString(): String {
        return name[0].toUpperCase().toString() + name.subSequence(1, name.length)
    }
}