package com.example.myapplication.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
            val part = exercise.getPart()?.getName() ?: "null"
            var draw = exercise.getPart()?.getImg() ?: 0
            (itemView.findViewById(R.id.iv_exercise) as ImageView).setImageResource(draw)
            (itemView.findViewById(R.id.tv_exercise) as TextView).text = exercise.getName()
            (itemView.findViewById(R.id.tv_type) as TextView).text = part

        }

        fun bindChild(exercise : Exercise){
            val child = itemView.findViewById(R.id.rv_sub_series) as RecyclerView
            val childLayoutManager = LinearLayoutManager(child.context, RecyclerView.VERTICAL, false)

            child.apply {
                layoutManager = childLayoutManager
                adapter = SeriesAdapter(exercise, resources)
                setRecycledViewPool(viewPool)
            }
        }
    }
}