package com.example.myapplication.exercises

import java.io.Serializable

open class Exercise(private var exerciseName:String, private var part: Part?,private var id:Int?):Serializable{
    constructor( ) : this("",null,0)

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
    fun getId(): Int?{
        return id
    }
    override fun toString(): String {
        return "$exerciseName $part "
    }
}