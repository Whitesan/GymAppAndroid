package com.example.myapplication.recycler_view

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ItemTouchHelperAdapter


class SimpleItemTouchHelperCallback(private var adapter: ItemTouchHelperAdapter) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        if (viewHolder.adapterPosition == adapter.getLastItem()){
            return makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.DOWN or ItemTouchHelper.UP)
        }
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START
        return makeMovementFlags(dragFlags, swipeFlags)
    }
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
    //TODO add on swipe delete and edit button
   override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
       adapter.onItemDismiss(viewHolder.adapterPosition)
    }
    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

}