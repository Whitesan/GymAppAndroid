package com.example.myapplication

class Exercise{

    val list = ArrayList<Series>()
    var exerciseName : String = "exercise"
    var seriesCount:Int = 0;
    fun addSeries(series: Series)
    {
        list.add(series)
        seriesCount++;
    }


}