package com.example.myapplication.recycler_view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ItemTouchHelperAdapter
import kotlin.math.max


class SimpleItemTouchHelperCallback(
    private var adapter: ItemTouchHelperAdapter,

    ) :
    ItemTouchHelper.Callback() {


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

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.9f
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
   ;

    }

    override fun onSelectedChanged(vh: RecyclerView.ViewHolder?, actionState: Int) {
        if (vh != null) {
            val viewHolder = vh as ExerciseViewHolder
            getDefaultUIUtil().onSelected(viewHolder.background)
        }
        super.onSelectedChanged(vh, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, vh: RecyclerView.ViewHolder) {
        //TODO there'ss something wrong, hide options
        super.clearView(recyclerView, vh)
        val viewHolder = vh as ExerciseViewHolder
//        hideOptions(vh)
        getDefaultUIUtil().clearView(viewHolder.foreground)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }
    private  var prevdx:Float = 0f
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
            if(prevdx < dX){
                hideOptions(viewHolder,actionState)
            }
            else if(prevdx != dX)
                showOptions(viewHolder, actionState)
            Log.println(Log.INFO,null,dX.toString()+" precDx:"+prevdx)
            prevdx=dX

            getDefaultUIUtil().onDraw(
                c, recyclerView, viewHolder.foreground, dX / 3, dY,
                actionState, isCurrentlyActive
            )
        } else{
            super.onChildDraw(c, recyclerView, myViewHolder, dX, dY, actionState, isCurrentlyActive)

        }


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
            if(prevdx < dX){
                hideOptions(viewHolder,actionState)
            }
            else if(prevdx!=dX)
                showOptions(viewHolder, actionState)
            prevdx=dX
            getDefaultUIUtil().onDrawOver(
                c, recyclerView, viewHolder.foreground, dX / 3, dY,
                actionState, isCurrentlyActive
            )
        }

        else{
            super.onChildDrawOver(
                c,
                recyclerView,
                myViewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
        }



    }


    private fun showOptions(vh: RecyclerView.ViewHolder, actionState: Int) {
        val viewHolder = vh as ExerciseViewHolder
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.background.visibility = View.VISIBLE
        }
    }

    private fun hideOptions(vh: RecyclerView.ViewHolder, actionState: Int) {
        val viewHolder = vh as ExerciseViewHolder
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.background.visibility = View.GONE

        }
    }


}