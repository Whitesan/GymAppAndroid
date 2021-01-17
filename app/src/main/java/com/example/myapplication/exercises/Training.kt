package com.example.myapplication.exercises

import java.io.Serializable


class Training(private var name:String, private var exerciseList :ArrayList<Exercise>) : Serializable
{
    private val id: Long = System.currentTimeMillis()
    private var days: ArrayList<Int> = ArrayList<Int>(7)
    fun getName (): String
    {
        return name;
    }
    fun getId():Long{
        return id;
    }
    fun showExercise():String
    {
        return exerciseList.toString()
    }


    fun getDays():ArrayList<Int>{
        return days;
    }
    fun hasDay(day: Int): Boolean{
        return days.contains(day)
    }


    fun getExercises():ArrayList<Exercise>{
        return exerciseList
    }
    fun setExercises(list:ArrayList<Exercise>){
       this.exerciseList = list
    }
}