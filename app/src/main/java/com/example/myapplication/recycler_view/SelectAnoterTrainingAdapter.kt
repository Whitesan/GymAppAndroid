package com.example.myapplication.recycler_view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Constants.Companion.PARTS_PER_LIST
import com.example.myapplication.R
import com.example.myapplication.activities.AppWindowActivity
import com.example.myapplication.activities.SelectAnotherTrainingActivity
import com.example.myapplication.activities.TrainingActivity
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Training

class SelectAnotherTrainingAdapter(
    private val trainingList: ArrayList<Training>,
    private val parentElement: AppWindowActivity
) : RecyclerView.Adapter<SelectAnotherTrainingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.select_another_training_list_element, parent, false)
        return ViewHolder(v, parentElement)
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

    class ViewHolder(itemView: View, private val parentElement :AppWindowActivity) : RecyclerView.ViewHolder(itemView) {
        private lateinit var training: Training
        fun bindItems(training: Training) {
            this.training= training
            val trainingName = itemView.findViewById(R.id.tv_training_type) as TextView
            trainingName.text = training.getName()
            var temp =""
            val set: MutableSet<String> = linkedSetOf()
            for (exercise: Exercise in training.getExercises()) {
                exercise.getPart()?.getStringId()?.let { set.add(parentElement.getString(it))
                }
            }


            var size=if(set.size > PARTS_PER_LIST) PARTS_PER_LIST else set.size
            var i=-1
            while(++i < size){
                temp+=" ${set.elementAt(i)}"
            }
            val text:TextView = itemView.findViewById(R.id.ListOfParts2)
            text.text = temp
        }
        fun getTraining():Training{
            return training
        }
    }
}
