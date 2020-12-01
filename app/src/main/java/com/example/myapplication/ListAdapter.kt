package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter<T>(private val listElements: ListElements<T>) : RecyclerView.Adapter<ListAdapter<T>.ViewHolder>(),ItemTouchHelperAdapter

{

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.listElement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter<T>.ViewHolder {
        val element = parent.context
        val inflater = LayoutInflater.from(element)
        val listElementView = inflater.inflate(R.layout.list_element_layout, parent, false)
        return ViewHolder(listElementView)
    }

    override fun onBindViewHolder(viewHolder: ListAdapter<T>.ViewHolder, position: Int) {
        val element = listElements.getAt(position)
        val textView = viewHolder.nameTextView
        textView.setText(element.toString())
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