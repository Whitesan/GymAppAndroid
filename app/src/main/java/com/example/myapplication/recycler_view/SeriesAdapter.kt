package com.example.myapplication.recycler_view


import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Series

class SeriesAdapter(
    private val ex: Exercise,
    private val resources: Resources
) : RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sub_element_exercise_list, parent, false), ex, resources)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(ex.list[position]) //ex.type ? cardio : normal
    }


    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return ex.list.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View, private val exercise :Exercise, private val resources: Resources) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(series : Series) {
            val part = exercise.getPart()?.getName() ?: "null"

            var text = ""
            if (part == "cardio") text = bindCardio(series)
            else text = bindNormal(series)


            (itemView.findViewById(R.id.tv_sub_series) as TextView).text = text
        }
        private fun bindCardio(series: Series):String{
            val str0 = resources.getString(R.string.series)
            val str1 = resources.getString(R.string.minutes)
            val str2 = resources.getString(R.string.meters)
            return "$str0: " + series.seriesNumber + " | $str1: " + series.reps + " | $str2: " + series.weight + " m"
        }
        private fun bindNormal(series: Series) :String{
            val str0 = resources.getString(R.string.series)
            val str1 = resources.getString(R.string.reps)
            val str2 = resources.getString(R.string.weight)
            return  "$str0: " + series.seriesNumber + " | $str1: " + series.reps + " | $str2: " + series.weight + " kg"
        }
    }
}