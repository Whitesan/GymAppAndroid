package com.example.myapplication.recycler_view

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.CreateTrainingActivity
import com.example.myapplication.activities.ExerciseActivity
import com.example.myapplication.activities.TrainingsListActivity
import com.example.myapplication.exercises.Exercise

class ExerciseViewHolder(view:View) : RecyclerView.ViewHolder(view) {
     var  foreground: View=itemView.findViewById(R.id.listElementLayout)
    var  background:View=itemView.findViewById(R.id.listElementLayoutBackground)
    private val nameTextView: TextView = itemView.findViewById(R.id.listElement)
    private val nameTextView2: TextView = itemView.findViewById(R.id.listElement2)
     val icon: ImageView = itemView.findViewById(R.id.ListElementIcon)
    private val edit:TextView = itemView.findViewById(R.id.editButton)
    fun bindExercise(exercise: Exercise){
        nameTextView.text=exercise.getName()
        nameTextView2.text=exercise.getPart().toString()
        icon.setImageURI(Uri.parse(exercise.getPart()?.getImg()))
        edit.setOnClickListener{
            val intent =  Intent(itemView.context, ExerciseActivity::class.java)
            intent.putExtra("editExercise",exercise)
            itemView.context.startActivity(intent)
            CreateTrainingActivity.editedIndex = exercise.getId()!!
        }
    }

}