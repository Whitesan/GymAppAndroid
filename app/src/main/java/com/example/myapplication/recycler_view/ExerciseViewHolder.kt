package com.example.myapplication.recycler_view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.CreateTrainingActivity
import com.example.myapplication.activities.EnterExerciseNameActivity
import com.example.myapplication.activities.ExerciseActivity
import com.example.myapplication.activities.TrainingsListActivity
import com.example.myapplication.exercises.Exercise

class ExerciseViewHolder(view:View) : RecyclerView.ViewHolder(view) {
    var  foreground: View=itemView.findViewById(R.id.listElementLayout)
    var  background:View=itemView.findViewById(R.id.listElementLayoutBackground)
    private val nameTextView: TextView = itemView.findViewById(R.id.listElement)
    private val nameTextView2: TextView = itemView.findViewById(R.id.listElement2)
     val icon: ImageView = itemView.findViewById(R.id.ListElementIcon)
    private val edit:ImageView = itemView.findViewById(R.id.editButton)
    private val delete:ImageView = itemView.findViewById(R.id.deleteListItem)
    @SuppressLint("ClickableViewAccessibility")
    fun bindExercise(exercise: Exercise){
        nameTextView.text=exercise.getName()
        nameTextView2.text=exercise.getPart().toString()
        icon.setImageURI(Uri.parse(exercise.getPart()?.getImg()))
        edit.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            val intent =  Intent(itemView.context, ExerciseActivity::class.java)
            intent.putExtra("editExercise",exercise)
            CreateTrainingActivity.editedIndex = exercise.getId()!!
            itemView.context.startActivity(intent)
            return@setOnTouchListener false;
        }
    }

}