package com.example.myapplication.exercises

import java.io.Serializable

open class Exercise(private var exerciseName:String, private var part: Part?,private var id:Int?):Serializable{
    constructor( ) : this("",null,0)

    companion object{
        var static_id:Long=0
    }
    private val PID= static_id++
    fun getPID(): Long{
        return PID
    }
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