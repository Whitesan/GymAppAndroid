package com.example.myapplication

import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

class ListElements{
    private var lastItem=0
    private var list:ArrayList<Exercise> = ArrayList<Exercise>()

    fun appendList(element:Exercise):Int{
        list.add(element)
        return list.size-1
    }
    fun getList():ArrayList<Exercise>{
        return list;
    }
    fun getSize():Int{
        return list.size
    }
    fun getAt(index:Int):Exercise{
        return list.get(index);
    }
    fun swap(index:Int, index2:Int){
        Collections.swap(list,index,index2)

    }
    fun remove(index:Int){
        list.removeAt(index)
    }
}