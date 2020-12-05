package com.example.myapplication.exercises

import java.io.Serializable

class Exercise(private var exerciseName:String, private var part: Part):Serializable{
    val list = ArrayList<Series>()
    fun addSeries(series: Series)
    {
        list.add(series)
    }
    fun getName():String{
        return exerciseName
    }
    fun getPart(): Part?{
        return part
    }
    override fun toString(): String {
        return "$exerciseName $part "
    }
}