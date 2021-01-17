package com.example.myapplication.recycler_view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Log.INFO
import android.util.Log.WARN
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.activities.CalendarActivity
import com.example.myapplication.activities.CalendarTrainingListActivity
import com.example.myapplication.activities.CreateTrainingActivity
import com.example.myapplication.activities.ExerciseListActivity
import com.example.myapplication.exercises.CurrentDay
import com.example.myapplication.exercises.Training
import com.example.myapplication.exercises.Trainings

class CalendarTrainingListAdapter(
    private val trainingList: ArrayList<Training>,
    private val savePath: String,
    private val parentView: CalendarTrainingListActivity,
    private val currentDay: Int
) : RecyclerView.Adapter<CalendarTrainingListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.element_calendar_trainings_list, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(trainingList[position])


        holder.itemView.setOnClickListener {
            onClickTraining(holder)
        }

        holder.itemView.setOnLongClickListener {
            return@setOnLongClickListener true
        }
    }

    private  fun onClickTraining(holder: ViewHolder){
        val id = holder.adapterPosition

        val context = holder.itemView.context

        for(t : Training in trainingList)
            if(t.hasDay(currentDay))
                t.getDays().remove(currentDay);

        trainingList[id].getDays().add(currentDay);

        val trainings = Trainings(trainingList)
        val json = TrainingJsonConverter()
        json.toJson(trainings, savePath)

        val intent = Intent(context, CalendarActivity::class.java)
        context.startActivity(intent)
        parentView.overridePendingTransition(R.anim.fade_in_animation, R.anim.slide_out_left_animation)
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