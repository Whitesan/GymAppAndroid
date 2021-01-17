package com.example.myapplication.exercises

import java.io.Serializable

data class Series (val seriesNumber: Int, var reps: Int, var weight: Int):Serializable{

    var restTime:Int? = null
    var exerciseTime:Int? = null
    var doneReps:Int? = null
    var doneWeight:Int? = null



}
