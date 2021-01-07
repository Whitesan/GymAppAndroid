package com.example.myapplication.exercises

import com.example.myapplication.R
import java.io.Serializable

class Part : Serializable {
    private lateinit var name: String
    private var drawId: Int = 0

    companion object Factory {
        private var map: HashMap<String, Part> = HashMap()

        init {
            map["chest"] = Part().setName("chest")
            map["back"] = Part().setName("back")
            map["shoulders"] = Part().setName("shoulders")
            map["cardio"] = Part().setName("cardio")
            map["triceps"] = Part().setName("triceps")
            map["biceps"] = Part().setName("biceps")
            map["abs"] = Part().setName("abs")
            map["calves"] = Part().setName("calves")
            map["neck"] = Part().setName("neck")
            map["thighs"] = Part().setName("thighs")
            map["forearms"] = Part().setName("forearms")
        }

        fun getPart(name: String): Part? {
            return map[name.toLowerCase()]
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