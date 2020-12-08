package com.example.myapplication.recycler_view

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise

class ExerciseViewHolder(view:View) : RecyclerView.ViewHolder(view) {
    val nameTextView: TextView = itemView.findViewById(R.id.listElement)
    val nameTextView2: TextView = itemView.findViewById(R.id.listElement2)
    val icon: ImageView = itemView.findViewById(R.id.ListElementIcon)
    fun bindExercise(exercise: Exercise){
        nameTextView.text=exercise.getName()
        nameTextView2.text=exercise.getPart().toString()
    }


}