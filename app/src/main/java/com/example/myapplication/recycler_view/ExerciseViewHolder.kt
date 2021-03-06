package com.example.myapplication.recycler_view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activities.CreateTrainingActivity
import com.example.myapplication.activities.ExerciseActivity
import com.example.myapplication.exercises.Exercise
import kotlin.properties.Delegates

class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var removed = false
    var foreground: View = itemView.findViewById(R.id.listElementLayout)
    var background: View = itemView.findViewById(R.id.listElementLayoutBackground)
    val nameTextView: TextView = itemView.findViewById(R.id.listElement)
    val icon: ImageView = itemView.findViewById(R.id.ListElementIcon)
    var  id: Int? = null
    private val nameTextView2: TextView = itemView.findViewById(R.id.listElement2)
    private val edit: ImageView = itemView.findViewById(R.id.editButton)
    private val delete: ImageView = itemView.findViewById(R.id.deleteListItem)
    lateinit var exercise: Exercise

    @SuppressLint("ClickableViewAccessibility")
    fun bindExercise(exercise: Exercise) {
        val draw = exercise.getPart()?.getImg() ?: 0
        nameTextView.text = exercise.getName()
        nameTextView2.text = exercise.getPart().toString()
        icon.setImageResource(draw)
        this.exercise = exercise
    }

    fun editButtonClicked(x: Float, y: Float): Int? {
        if (edit.visibility == View.VISIBLE && x > edit.x && x < edit.x + edit.width) {
            if (y > itemView.y && y < itemView.y + itemView.height) {
                return adapterPosition
            }
        }
        return null
    }

    fun deleteButtonClicked(x: Float, y: Float): Int? {
        Log.println(
            Log.INFO,
            null,
            "Clicked (x $x y $y),button (x ${delete.x} ${delete.x + delete.width}) (y ${itemView.y} to ${itemView.y + itemView.height}) "
        )
        if (delete.visibility == View.VISIBLE && x > delete.x && x < delete.x + delete.width) {
            if (y > itemView.y && y < itemView.y + itemView.height) {
                removed = true
                return adapterPosition
            }
        }
        return null
    }

    fun viewClicked(x: Float, y: Float): Boolean {
        if (x > foreground.x && x < foreground.x + foreground.width) {
            if (y > foreground.y && y < foreground.y + foreground.height) {
                return true
            }
        }
        return false
    }

    fun editExercise() {
        val intent = Intent(itemView.context, ExerciseActivity::class.java)
        intent.putExtra("editExercise", exercise)
        CreateTrainingActivity.editedIndex = exercise.getId()!!
        itemView.context.startActivity(intent)
    }

}