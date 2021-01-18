package com.example.myapplication.exercises

import android.util.Log
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
    fun getTrainingByDay(day: Int?):Training?{
        for(training in trainingList){
           if(training.getDays().isNotEmpty())
           {
               for(d in training.getDays())
               {
                   if(day == d)
                   {
                       return training;
                   }

               }
           }
        }
        return null
    }
/*
    fun getTrainingByDay(day: Int?):Training?{
        for(training in trainingList){
            if(training.getDay()==day)
                return training
        }
        return null
    }
    */




}