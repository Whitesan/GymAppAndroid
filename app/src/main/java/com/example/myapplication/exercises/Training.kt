package com.example.myapplication.exercises

import java.io.Serializable
import java.time.LocalDate


class Training(private var name:String, private var exerciseList :ArrayList<Exercise>) : Serializable
{
    private var day: Int? = null
    private var dateInMillis:Long? = null
    fun getName (): String
    {
        return name;
    }
    fun showExercise():String
    {
        return exerciseList.toString()
    }
    fun setDate(){
        this.dateInMillis=System.currentTimeMillis()
    }
    fun getDate():Long?{
        return dateInMillis
    }
    fun getExercises():ArrayList<Exercise>{
        return exerciseList
    }
    fun setExercises(list:ArrayList<Exercise>){
       this.exerciseList = list
    }
}