package com.example.myapplication.recycler_view

import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.exercises.Training
import java.util.*


class TrainingListAdapter(private val trainingList: ArrayList<Training>) : RecyclerView.Adapter<TrainingListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.element_trainings_list, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: TrainingListAdapter.ViewHolder, position: Int) {
        holder.bindItems(trainingList[position])



        holder.itemView.setOnClickListener{
            val id = holder.adapterPosition
            Collections.swap(trainingList, id, 0)
            notifyItemMoved(0, id)
            Log.i("recycle", "clicked " + id.toString())
        }

        holder.itemView.setOnLongClickListener{
            val id = holder.adapterPosition
            Log.i("recycle", "long clicked " + id.toString())
            return@setOnLongClickListener true
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return trainingList.size
    }

    //the class is holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(training: Training) {
            val trainingName = itemView.findViewById(R.id.tv_training_type) as TextView
            trainingName.text = training.getName()
        }
    }
}