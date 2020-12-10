package com.example.myapplication.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.exercises.Exercise
import java.util.*


class ExerciseAdapter(private val viewType: Int, trainingItems:ArrayList<Exercise>) {
    fun getViewType(): Int {
        return viewType
    }

    fun isForViewType(items: LinkedList<Exercise>, position: Int): Boolean {
        return !(items[position] is  AddButton)
    }

    fun onCreateViewHolder(view: View): RecyclerView.ViewHolder {
        return ExerciseViewHolder(view)
    }

    fun onBindViewHolder(
        items: LinkedList<Exercise>,
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        val exercise: Exercise = items[position] as Exercise
        val vh: ExerciseViewHolder = holder as ExerciseViewHolder
        vh.bindExercise(exercise)
    }



}