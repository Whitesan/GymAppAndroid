package com.example.myapplication.exercises

import java.io.Serializable

class Trainings (var trainingList :ArrayList<Training>):Serializable{
    fun showTrainings():String
    {
        var list : String =""
        for (t in trainingList)
        {
            list += t.getName()
        }
        return list;
    }

}