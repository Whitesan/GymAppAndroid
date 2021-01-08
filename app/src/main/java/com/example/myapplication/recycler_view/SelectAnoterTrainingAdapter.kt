package com.example.myapplication.recycler_view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.TrainingActivity
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Training

class SelectAnotherTrainingAdapter(
    private val trainingList: ArrayList<Training>,
) : RecyclerView.Adapter<SelectAnotherTrainingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.select_another_training_list_element, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(trainingList[position])
        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, TrainingActivity::class.java)
            intent.putExtra("Training", holder.getTraining())
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var training: Training
        fun bindItems(training: Training) {
            this.training= training
            val trainingName = itemView.findViewById(R.id.tv_training_type) as TextView
            trainingName.text = training.getName()
            var temp =""
            val set: MutableSet<String> = linkedSetOf()
            for (exercise: Exercise in training.getExercises()) {
                exercise.getPart()?.getName()?.let { set.add(it)
                }
            }
            for(s in set){
                temp+=" $s"
            }
            val text:TextView = itemView.findViewById(R.id.ListOfParts2)
            text.text = temp
        }
        fun getTraining():Training{
            return training
        }
    }
}
