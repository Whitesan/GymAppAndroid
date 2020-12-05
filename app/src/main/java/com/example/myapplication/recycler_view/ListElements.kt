package com.example.myapplication

import com.example.myapplication.exercises.Exercise
import com.example.myapplication.recycler_view.AddButton
import java.util.*
import kotlin.collections.ArrayList

class ListElements{
    private var lastItem=0
    private var list:LinkedList<Exercise> = LinkedList<Exercise>()
    fun appendList(element: Exercise):Int{

        list.add(list.size-1,element)
        return list.indexOf(element)
    }
    fun addButton(element: AddButton):Int{
        list.add(element)
        return list.indexOf(element)
    }
    fun getList():LinkedList<Exercise>{
        return list;
    }
    fun getSize():Int{
        return list.size
    }
    fun getAt(index:Int): Exercise {
        return list.get(index);
    }
    fun swap(index:Int, index2:Int){
        if(index!=list.size-1 && index2!=list.size-1)
            Collections.swap(list,index,index2)
    }
    fun remove(index:Int){
        list.removeAt(index)
    }
}