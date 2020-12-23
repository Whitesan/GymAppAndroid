package com.example.myapplication.recycler_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.*
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise

class ExerciseListAdapter(private val list : ArrayList<Exercise>) : RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListAdapter.ViewHolder {
        val layout = R.layout.element_exercise_list
        val v = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ViewHolder(v, viewPool)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ExerciseListAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
        holder.bindChild(list[position])
    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return list.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View, private val viewPool : RecyclerView.RecycledViewPool) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(exercise : Exercise) {
            (itemView.findViewById(R.id.tv_exercise) as TextView).text = exercise.getName()
            (itemView.findViewById(R.id.tv_type) as TextView).text = exercise.getPart()?.getName() ?: "null"
            //(itemView.findViewById(R.id.tv_info) as TextView).text = "tenp\ntemp"

            val id = exercise.getPart()?.getName() ?: "null"
            var draw = R.drawable.chest
            when(id){
                "chest" -> draw=R.drawable.chest
                "back" -> draw= R.drawable.back
                "shoulders" -> draw= R.drawable.shoulders
                "cardio" -> draw=R.drawable.cardio
                "triceps" -> draw=R.drawable.triceps
                "biceps" -> draw=R.drawable.biceps
                "neck" -> draw=R.drawable.neck
                "forearms" -> draw= R.drawable.forearm
                "thighs" -> draw= R.drawable.thighs
                "calves" -> draw=R.drawable.calves
                "abs" -> draw=R.drawable.abs
            }
            (itemView.findViewById(R.id.iv_exercise) as ImageView).setImageResource(draw)

        }

        fun bindChild(exercise : Exercise){
            val child = itemView.findViewById(R.id.rv_sub_series) as RecyclerView
            val childLayoutManager = LinearLayoutManager(child.context, RecyclerView.VERTICAL, false)

            child.apply {
                layoutManager = childLayoutManager
                adapter = SeriesAdapter(exercise)
                setRecycledViewPool(viewPool)
            }
        }
    }
}