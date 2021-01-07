package com.example.myapplication.exercises

import java.io.Serializable


class Training(private var name:String, var exerciseList :ArrayList<Exercise>) : Serializable
{
    private var day: Int? = null
    fun getName (): String
    {
        return name;
    }
    fun showExercise():String
    {
        return exerciseList.toString()
    }
    fun setDay(day:Int?){
        this.day=day
    }
    fun getDay():Int?{
        return day;
    }

}