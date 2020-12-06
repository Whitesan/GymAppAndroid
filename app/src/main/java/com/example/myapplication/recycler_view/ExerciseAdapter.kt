package com.example.myapplication.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.exercises.Exercise
import java.util.*


class ExerciseAdapter(private val viewType: Int) {
    fun getViewType(): Int {
        return viewType
    }

    fun isForViewType(items: LinkedList<Exercise>, position: Int): Boolean {
        return !(items[position] is  AddButton)
    }

    fun onCreateViewHolder(view: View): RecyclerView.ViewHolder {
        return ExerciseViewHolder(view)
    }

    fun onBindViewHolder(
        items: LinkedList<Exercise>,
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        val exercise: Exercise = items[position] as Exercise
        val vh: ExerciseViewHolder = holder as ExerciseViewHolder
        vh.bindExercise(exercise)
    }


//
//
//    fun bind(element: Exercise, clickListener: View.OnTouchListener) {
//        nameTextView.text = element.getName()
//        nameTextView2.text = element.getPart().toString()
//    }
//
//
//    fun setAsButton(element: AddButton, itemTouchListener: View.OnTouchListener) {
//        if(!buttonAdded){
//            buttonAdded=true;
//            //TODO sometimes new button appears(!?). Usually after 8-9 exercise. DEBUG !!!!!
//            nameTextView.visibility = View.INVISIBLE
//            nameTextView2.visibility = View.INVISIBLE
//            icon.visibility = View.INVISIBLE
//            newExerciseButton.visibility = View.VISIBLE
//            var buttonDrawable: Drawable? = listElementLayout.background
//            buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)
//            DrawableCompat.setTint(buttonDrawable, Color.parseColor("#375bed"))
//            listElementLayout.background = buttonDrawable
//            listElementLayout.setOnTouchListener(itemTouchListener)
//        }
//
//    }

}