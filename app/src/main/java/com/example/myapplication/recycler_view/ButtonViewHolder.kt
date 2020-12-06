package com.example.myapplication.recycler_view

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ButtonViewHolder(view:View) : RecyclerView.ViewHolder(view){
    val nameTextView: TextView = itemView.findViewById(R.id.listElement)
    val nameTextView2: TextView = itemView.findViewById(R.id.listElement2)
    val icon: ImageView = itemView.findViewById(R.id.ListElementIcon)
    val newExerciseButton: TextView = itemView.findViewById(R.id.AddNewExerciseButton)
    val listElementLayout: RelativeLayout = itemView.findViewById(R.id.listElementLayout)
     fun bindButton(itemTouchListener:View.OnTouchListener){
         nameTextView.visibility=View.INVISIBLE
         nameTextView2.visibility=View.INVISIBLE
         icon.visibility=View.INVISIBLE
         newExerciseButton.visibility=View.VISIBLE
         var buttonDrawable: Drawable = listElementLayout.background
            buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)
            DrawableCompat.setTint(buttonDrawable, Color.parseColor("#375bed"))
            listElementLayout.background = buttonDrawable
            listElementLayout.setOnTouchListener(itemTouchListener)
    }

}