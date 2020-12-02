package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter<Exercise>(private val listElements: ListElements) : RecyclerView.Adapter<ListAdapter<Exercise>.ViewHolder>(),ItemTouchHelperAdapter

{

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.listElement)
        val nameTextView2 = itemView.findViewById<TextView>(R.id.listElement2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter<Exercise>.ViewHolder {
        val element = parent.context
        val inflater = LayoutInflater.from(element)
        val listElementView = inflater.inflate(R.layout.list_element_layout, parent, false)
        return ViewHolder(listElementView)
    }

    override fun onBindViewHolder(viewHolder: ListAdapter<Exercise>.ViewHolder, position: Int) {
        val element = listElements.getAt(position)
        val textView = viewHolder.nameTextView
        textView.setText(element.getName())
        val PartTextView = viewHolder.nameTextView2
        textView.setText(element.getName())
        PartTextView.setText(element.getPart().toString())
    }
    override fun getItemCount(): Int {
        return listElements.getSize()
    }

    // itemTouchHelperAdapter interface
    override fun onItemDismiss(position: Int) {
        listElements.remove(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
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