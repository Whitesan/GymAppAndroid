package com.example.myapplication

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int):Boolean
    fun onItemDismiss(position: Int)
    fun getLastItem():Int
}