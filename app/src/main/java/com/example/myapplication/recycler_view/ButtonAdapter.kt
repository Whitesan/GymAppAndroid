package com.example.myapplication.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.exercises.Exercise
import java.util.*

class ButtonAdapter(private val viewType: Int) {
    fun getViewType(): Int {
        return viewType
    }

    fun isForViewType(items: LinkedList<Exercise>, position: Int): Boolean {
        return items[position] is  AddButton
    }

    fun onCreateViewHolder(view: View): RecyclerView.ViewHolder {
        return ButtonViewHolder(view)
    }

    fun onBindViewHolder(
        items: LinkedList<Exercise>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        itemTouchListener: View.OnTouchListener
    ) {
//        val button: AddButton = items[position] as AddButton
        val vh: ButtonViewHolder = holder as ButtonViewHolder
        vh.bindButton(itemTouchListener)

    }
}