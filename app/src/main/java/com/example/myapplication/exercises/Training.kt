package com.example.myapplication.exercises

import java.io.Serializable


class Training(private var name:String, private var exerciseList :ArrayList<Exercise>) : Serializable
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
    fun getExercises():ArrayList<Exercise>{
        return exerciseList
    }
    fun setExercises(list:ArrayList<Exercise>){
       this.exerciseList = list
    }
}