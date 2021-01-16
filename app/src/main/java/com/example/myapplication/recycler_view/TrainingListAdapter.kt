package com.example.myapplication.recycler_view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.ExerciseListActivity
import com.example.myapplication.exercises.Training
import java.util.*
import kotlin.collections.ArrayList
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.activities.AppWindowActivity
import com.example.myapplication.activities.CreateTrainingActivity
import com.example.myapplication.activities.TrainingsListActivity
import com.example.myapplication.exercises.Trainings

class TrainingListAdapter(
    private val trainingList: ArrayList<Training>,
    private val savePath: String,
    private val parentView: TrainingsListActivity
) : RecyclerView.Adapter<TrainingListAdapter.ViewHolder>() {
    companion object {
        var currentTraining = Training("", ArrayList())
    }

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrainingListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.element_trainings_list, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: TrainingListAdapter.ViewHolder, position: Int) {
        holder.bindItems(trainingList[position])

        val edit = holder.itemView.findViewById<ImageView>(R.id.editButton)
        val delete = holder.itemView.findViewById<ImageView>(R.id.deleteListItem)

        edit.setOnClickListener {
            runEditTrainingWindow(trainingList[holder.adapterPosition], holder.itemView.context)
        }


        val builder = AlertDialog.Builder(holder.itemView.context, R.style.AlertDialog)
        delete.setOnClickListener {
            deleteDialogAction(builder, holder.adapterPosition)
        }

        holder.itemView.setOnClickListener {
            onClickTraining(holder)
        }

        holder.itemView.setOnLongClickListener {
            return@setOnLongClickListener true
        }
    }

    private  fun onClickTraining(holder: ViewHolder){
        val id = holder.adapterPosition
        currentTraining = trainingList[id]

        val context = holder.itemView.context
        val intent = Intent(context, ExerciseListActivity::class.java)
        context.startActivity(intent)
        parentView.overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)

    }

    private  fun deleteDialogAction(builder: AlertDialog.Builder, position: Int){
        val res = parentView.resources
        val title = res.getString(R.string.TLA_Title_Dialog)
        val msg = res.getString(R.string.TLA_Msg_Dialog)
        val name = trainingList[position].getName()
        val yes = res.getString(R.string.TLA_YES)
        val no = res.getString(R.string.TLA_NO)

        builder.setTitle(title)
        builder.setMessage("$msg $name?")

        builder.setPositiveButton(yes) { dialog, which ->
            removeItemPermanent(position)
        }

        builder.setNegativeButton(no) { dialog, which ->
            return@setNegativeButton
        }
        builder.show()
    }

    private fun removeItemPermanent(position: Int) {
        trainingList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)

        //Save to JSON
        val trainings = Trainings(trainingList)
        val json: TrainingJsonConverter = TrainingJsonConverter()
        json.toJson(trainings, savePath)
    }

    private fun runEditTrainingWindow(training:Training, context: Context){
        CreateTrainingActivity.exerciseList = ArrayList(training.getExercises())
        CreateTrainingActivity.enteredText = training.getName()
        val intent =  Intent(context, CreateTrainingActivity::class.java)
        parentView.startActivity(intent)
        parentView.overridePendingTransition(R.anim.fade_in_animation,R.anim.fade_out_animation)

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