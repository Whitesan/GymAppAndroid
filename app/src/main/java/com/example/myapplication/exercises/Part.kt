package com.example.myapplication.exercises

import java.io.Serializable

class Part:Serializable{
    private lateinit var name:String
    companion object Factory{
        private var map:HashMap<String,Part> = HashMap<String,Part>()
        init{
            map.put("plecy",Part().setName("plecy"))
            map.put("klata",Part().setName("klata"))
            map.put("biceps",Part().setName("biceps"))
            map.put("triceps",Part().setName("triceps"))


        }
        fun getPart(name:String):Part?{
            return map.get(name.toLowerCase())
        }
    }
    private fun setName(name: String):Part{
        this.name=name.toLowerCase()
        return this
    }
    public fun getName():String{
        return name
    }
    override fun toString(): String {
        return name
    }
}