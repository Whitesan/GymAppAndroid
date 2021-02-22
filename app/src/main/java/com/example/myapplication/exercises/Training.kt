package com.example.myapplication.exercises

import java.io.Serializable
import java.time.LocalDateTime


class Training(private var name:String, private var exerciseList :ArrayList<Exercise>) : Serializable
{
    private val id: Long = System.currentTimeMillis()
    private var days: ArrayList<Int> = ArrayList<Int>(7)

    var day: Int = -1
    var month: Int = -1
    var year: Int = -1

    fun getWorkoutDate(): String {
        return "$year-$month-$day"
    }
    fun setWorkoutDate(workoutDate:LocalDateTime) {
        day = workoutDate.dayOfMonth
        month = workoutDate.monthValue
        year = workoutDate.year
    }
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