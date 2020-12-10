package com.example.myapplication.recycler_view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ItemTouchHelperAdapter


class SimpleItemTouchHelperCallback(private var adapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (viewHolder.adapterPosition == adapter.getLastItem()) {
            return makeFlag(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.DOWN or ItemTouchHelper.UP
            )
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
//        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val myViewHolder: ExerciseViewHolder = viewHolder as ExerciseViewHolder

        if (dX < 0) {
            val myViewHolder: ExerciseViewHolder = viewHolder as ExerciseViewHolder
            getDefaultUIUtil().onDraw(
                c,
                recyclerView,
                myViewHolder.foreground,
                dX / 3f,
                dY,
                actionState,
                isCurrentlyActive

            )
        }
        else
            super.onChildDraw(c, recyclerView, myViewHolder, dX, dY, actionState, isCurrentlyActive)


    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val myViewHolder: ExerciseViewHolder = viewHolder as ExerciseViewHolder
        if (dX < 0) {
            getDefaultUIUtil().onDrawOver(
                c,
                recyclerView,
                myViewHolder.foreground,
                dX / 3f,
                dY,
                actionState,
                isCurrentlyActive
            )
        }
        else
            super.onChildDrawOver(c, recyclerView, myViewHolder, dX, dY, actionState, isCurrentlyActive)
    }
    fun showOptions(){

    }

}