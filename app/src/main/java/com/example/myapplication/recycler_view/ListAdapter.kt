package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.recycler_view.ButtonAdapter
import com.example.myapplication.recycler_view.ExerciseAdapter
import java.util.*


class ListAdapter(private val itemTouchListener: View.OnTouchListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {
    private val items: LinkedList<Exercise> = LinkedList<Exercise>()
    private val VIEW_TYPE_EXERCISE = 0
    private val VIEW_TYPE_BUTTON = 1
    private val buttonAdapter: ButtonAdapter = ButtonAdapter(VIEW_TYPE_BUTTON)
    private val exerciseAdapter: ExerciseAdapter = ExerciseAdapter(VIEW_TYPE_EXERCISE)

    override fun getItemViewType(position: Int): Int {
        print(position)
        if (buttonAdapter.isForViewType(items, position)) {
            return buttonAdapter.getViewType()
        } else if (exerciseAdapter.isForViewType(items, position)) {
            return exerciseAdapter.getViewType()
        }
        throw IllegalArgumentException("Illegal argument!")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (buttonAdapter.getViewType() == viewType) {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_element_layout, parent, false)
            return buttonAdapter.onCreateViewHolder(view)
        } else if (exerciseAdapter.getViewType() == viewType) {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_element_layout, parent, false)
            return exerciseAdapter.onCreateViewHolder(view)
        }
        throw java.lang.IllegalArgumentException("No delegate found")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType: Int = holder.itemViewType
        if (buttonAdapter.getViewType() == viewType) {
            buttonAdapter.onBindViewHolder(items, position, holder, itemTouchListener)
        } else if (exerciseAdapter.getViewType() == viewType) {
            exerciseAdapter.onBindViewHolder(items, position, holder)
        }
//        if(position==0){
//            buttonAdapter.onBindViewHolder(items, position, holder,itemTouchListener)
//        }
//        else{
//            exerciseAdapter.onBindViewHolder(items, position, holder)
//        }

    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (toPosition == items.size - 1)
            return false

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun getLastItem(): Int {
        return items.size - 1
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onItemDismiss(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun appendItem(item: Exercise,recycler:RecyclerView) {
        if (items.size == 0) {
            items.add(item)
            notifyItemInserted(items.size-1)

        } else {
            items.add(items.size -1,item)
            notifyItemInserted(items.size-2)
        }
        recycler.scrollToPosition(items.size-1)
    }
}