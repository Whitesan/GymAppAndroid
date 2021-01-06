package com.example.myapplication.recycler_view


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Series

class SeriesAdapter(private val ex : Exercise) : RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sub_element_exercise_list, parent, false), ex)
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
    class ViewHolder(itemView: View, private val exercise :Exercise) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(series : Series) {
            val part = exercise.getPart()?.getName() ?: "null"

            var text = ""
            if (part.equals("cardio")) text = bindCardio(series)
            else text = bindNormal(series)

            Log.i("SeriesAdapter", text)
            (itemView.findViewById(R.id.tv_sub_series) as TextView).text = text
        }
        fun bindCardio(series: Series):String{ //ex.type
            return "Series: " + series.seriesNumber + " | Minutes: " + series.reps + " | Meters: " + series.weight + " m"
        }
        private fun bindNormal(series: Series) :String{ //ex.type
            return  "Series: " + series.seriesNumber + " | Reps: " + series.reps + " | Weight: " + series.weight + " kg"
        }
    }
}