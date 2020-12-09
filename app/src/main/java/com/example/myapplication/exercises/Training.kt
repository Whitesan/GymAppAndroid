package com.example.myapplication.exercises

import java.io.Serializable

class Training(private var name:String, var exerciseList :ArrayList<Exercise>) : Serializable
{
    fun getName (): String
    {
        return name;
    }
    fun showExercise():String
    {
        return exerciseList[0].getName()
    }
}