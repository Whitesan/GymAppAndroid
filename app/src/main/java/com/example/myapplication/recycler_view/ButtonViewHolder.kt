package com.example.myapplication.recycler_view

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ButtonViewHolder(view:View) : RecyclerView.ViewHolder(view){
    val layout: CardView =itemView.findViewById(R.id.listButtonElementLayout)
    val newExerciseButton: TextView = itemView.findViewById(R.id.AddNewExerciseButton)
     fun bindButton(itemTouchListener:View.OnTouchListener){
         newExerciseButton.visibility=View.VISIBLE

         layout.setOnTouchListener(itemTouchListener)

    }

}