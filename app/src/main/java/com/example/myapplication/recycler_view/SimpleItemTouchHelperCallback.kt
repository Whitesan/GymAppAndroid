package com.example.myapplication.recycler_view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ItemTouchHelperAdapter
import com.example.myapplication.activities.CreateTrainingActivity
import com.example.myapplication.activities.ExerciseActivity


class SimpleItemTouchHelperCallback(
    private var adapter: ItemTouchHelperAdapter,
) :View.OnTouchListener,
    ItemTouchHelper.Callback(){

    private  var prevdx:Float = 0f
    private  val leftSwipeThreshold=0.9f
    private  val rightSwipeThreshold=0.6f
    private  var activeThreshold=leftSwipeThreshold
    private var showed:Boolean=false
    var clicked_x = 0f;
    var clicked_y = 0f;
     var activeViewHolder:ExerciseViewHolder? = null;



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
        return activeThreshold
    }

    override fun onSelectedChanged(vh: RecyclerView.ViewHolder?, actionState: Int) {
        if (vh != null) {
            val viewHolder = vh as ExerciseViewHolder
            getDefaultUIUtil().onSelected(viewHolder.background)
        }
        super.onSelectedChanged(vh, actionState)
    }
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.println(Log.INFO, null, " Swiped !!! ")
        showOptions(viewHolder, ItemTouchHelper.ACTION_STATE_SWIPE)
        showed=true;

    }
    override fun clearView(recyclerView: RecyclerView, vh: RecyclerView.ViewHolder) {
        //TODO there'ss something wrong, hide options

        Log.println(Log.INFO, null, "[prevdx:  " + prevdx)
        super.clearView(recyclerView, vh)

        if(showed && prevdx >=-10.0f){
            val viewHolder = vh as ExerciseViewHolder
//        //hideOptions(viewHolder,ItemTouchHelper.ACTION_STATE_SWIPE)
            getDefaultUIUtil().clearView(viewHolder.foreground)
//            super.clearView(recyclerView, vh)
            hideOptions(viewHolder, ItemTouchHelper.ACTION_STATE_SWIPE)
        }
//        else {
//            super.clearView(recyclerView, vh)
//        }
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
        activeViewHolder=viewHolder as ExerciseViewHolder
        Log.println(Log.INFO, null, "OnChildDraw")
        val myViewHolder: ExerciseViewHolder = viewHolder as ExerciseViewHolder
        if (dX < 0) {
            if(prevdx < dX){

                activeThreshold=leftSwipeThreshold

            }
            prevdx=dX
            activeThreshold=rightSwipeThreshold
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
//                hideOptions(viewHolder,actionState)
                activeThreshold=leftSwipeThreshold

            }
            else if(prevdx!=dX)
//                showOptions(viewHolder, actionState)
            prevdx=dX
            activeThreshold=rightSwipeThreshold

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
                dX / 3,
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
            showed=true
        }
    }

    private fun hideOptions(vh: RecyclerView.ViewHolder, actionState: Int) {
        val viewHolder = vh as ExerciseViewHolder
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.background.visibility = View.GONE
            showed=false
        }
    }

    override fun onTouch(p0: View?, p1: MotionEvent): Boolean {
         clicked_x = p1.x
         clicked_y = p1.y
        if(p1.action==MotionEvent.ACTION_DOWN && showed){
            if(activeViewHolder?.editButtonClicked(clicked_x,clicked_y) == true){
                activeViewHolder?.editExercise()
                Log.println(Log.INFO,null,"BUTTON EDIT PRESSED!")
                return true;
            }
            else if(activeViewHolder?.deleteButtonClicked(clicked_x,clicked_y) == true){
               deleteExercise()
                Log.println(Log.INFO,null,"DELETE BUTTON PRESSED!")
                return true;
            }
        }

        return false;

    }

    @SuppressLint("ClickableViewAccessibility")
    fun setListener(recycler: RecyclerView) {
        recycler.setOnTouchListener(this)
    }
    private fun deleteExercise(){
        activeViewHolder?.position?.let { adapter.onItemDismiss(it) }
    }

}