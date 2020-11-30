package com.example.myapplication

class Exercise(){
    var exerciseName : String="none"
    val list = ArrayList<Series>()

    fun addSeries(series: Series)
    {
        list.add(series)
    }
    fun addName(name:String?){
        if(name!=null){
            exerciseName=name
        }

    }

    override fun toString(): String {
        return exerciseName+ " "+ list.size;
    }
}