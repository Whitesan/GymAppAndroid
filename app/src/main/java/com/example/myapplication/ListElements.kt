package com.example.myapplication

import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

class ListElements <T>{
    private var lastItem=0
    private var list:ArrayList<T> = ArrayList<T>()

    fun appendList(element:T):Int{
        list.add(element)
        return list.size-1
    }
    fun getList():ArrayList<T>{
        return list;
    }
    fun getSize():Int{
        return list.size
    }
    fun getAt(index:Int):T{
        return list.get(index);
    }
    fun swap(index:Int, index2:Int){
        Collections.swap(list,index,index2)

    }
    fun remove(index:Int){
        list.removeAt(index)
    }
}