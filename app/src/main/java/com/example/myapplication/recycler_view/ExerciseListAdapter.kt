package com.example.myapplication.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise

class ExerciseListAdapter(private val list : ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListAdapter.ViewHolder {
        val layout = R.layout.element_exercise_list
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ExerciseListAdapter.ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ExerciseListAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(exercise : Exercise) {
            val xmlName = R.id.tv_exercise
            (itemView.findViewById(xmlName) as TextView).text = exercise.getName()

            val xmlTes = R.id.tes_id
            (itemView.findViewById(xmlTes) as TextView).text = exercise.getPart()?.getName() ?: "null"

        }
    }
}