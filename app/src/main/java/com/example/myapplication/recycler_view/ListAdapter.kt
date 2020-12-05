package com.example.myapplication

import android.R.attr.button
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.activities.CreateTrainingActivity
import com.example.myapplication.activities.PlannerActivity
import com.example.myapplication.activities.StatisticsActivity
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.recycler_view.AddButton


class ListAdapter<Exercise>(private val listElements: ListElements) : RecyclerView.Adapter<ListAdapter<Exercise>.ViewHolder>(),ItemTouchHelperAdapter
{
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView:TextView = itemView.findViewById(R.id.listElement)
        val nameTextView2:TextView = itemView.findViewById(R.id.listElement2)
        val icon:ImageView = itemView.findViewById(R.id.ListElementIcon)
        val newExerciseButton: TextView = itemView.findViewById(R.id.AddNewExerciseButton)
        val listElementLayout:RelativeLayout= itemView.findViewById(R.id.listElementLayout)
        val size:Int=listElements.getSize()
    }

    override fun getLastItem(): Int {
        return listElements.getSize()-1
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter<Exercise>.ViewHolder {
        val element = parent.context
        val inflater = LayoutInflater.from(element)
        val listElementView = inflater.inflate(R.layout.list_element_layout, parent, false)
        return ViewHolder(listElementView)
    }

    override fun onBindViewHolder(viewHolder: ListAdapter<Exercise>.ViewHolder, position: Int) {

        val element = listElements.getAt(position)
        if(element is AddButton && element == listElements.getAt(listElements.getSize()-1)){
            setAsButton(viewHolder,element)

        }
        else{
            val textView = viewHolder.nameTextView
            textView.text = element.getName()
            val partTextView = viewHolder.nameTextView2
            partTextView.text = element.getPart().toString()
        }

    }
    private fun setAsButton(viewHolder: ListAdapter<Exercise>.ViewHolder,element:AddButton){

        //TODO
        //sometimes new button appears(!?). Usually after 8-9 exercise. DEBUG !!!!!
        val textView = viewHolder.nameTextView
        textView.visibility = View.INVISIBLE;
        val partTextView = viewHolder.nameTextView2
        partTextView.text = element.getPart().toString()
        partTextView.visibility = View.INVISIBLE;
        val icon = viewHolder.icon
        icon.visibility = View.INVISIBLE
        val newExerciseButton=viewHolder.newExerciseButton
        newExerciseButton.visibility = View.VISIBLE
        val listElementLayout =viewHolder.listElementLayout

        var buttonDrawable: Drawable? = listElementLayout.background
        buttonDrawable = DrawableCompat.wrap(buttonDrawable!!)
        DrawableCompat.setTint(buttonDrawable, Color.BLUE)
        listElementLayout.background = buttonDrawable
    }


    override fun getItemCount(): Int {
        return listElements.getSize()
    }

    override fun onItemDismiss(position: Int) {
        listElements.remove(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if(toPosition==listElements.getSize()-1) return false

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                listElements.swap(i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                listElements.swap(i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }
}