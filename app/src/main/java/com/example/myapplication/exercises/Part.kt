package com.example.myapplication.exercises

import java.io.Serializable

class Part(private val name : String):Serializable{


    override fun toString(): String {
        return name
    }
}