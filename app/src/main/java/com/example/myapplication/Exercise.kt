package com.example.myapplication

class Exercise(private var exerciseName:String,private var part:Part ){
    val list = ArrayList<Series>()
    fun addSeries(series: Series)
    {
        list.add(series)
    }
    fun getName():String{
        return exerciseName
    }
    fun getPart():Part?{
        return part
    }
    override fun toString(): String {
        return "$exerciseName $part "
    }
}