package com.example.myapplication.exercises

import java.io.Serializable

class Calendar  : Serializable {
    var dayList :ArrayList<Day> = ArrayList()
    fun init()
    {
        for(i in 0 until 7)
        {
            dayList.add(Day(i+1,"empty"))
        }
    }
}