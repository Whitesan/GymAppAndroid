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
import com.example.myapplication.ListAdapter
import com.example.myapplication.activities.CreateTrainingActivity
import com.example.myapplication.activities.ExerciseActivity


class SimpleItemTouchHelperCallback(
    private var adapter: ListAdapter,
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
    var cleared = false

    var removed=false
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


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.println(Log.INFO, null, " Swiped !!! ")
        showOptions(viewHolder, ItemTouchHelper.ACTION_STATE_SWIPE)
        if(activeViewHolder!=viewHolder){
            activeViewHolder=viewHolder as ExerciseViewHolder
        }

    }

        override fun onSelectedChanged(vh: RecyclerView.ViewHolder?, actionState: Int) {
        if (vh != null) {
            val viewHolder = vh as ExerciseViewHolder
            getDefaultUIUtil().onSelected(viewHolder.background)
        }
        super.onSelectedChanged(vh, actionState)
    }
//    override fun clearView(recyclerView: RecyclerView, vh: RecyclerView.ViewHolder) {
//        super.clearView(recyclerView, vh)
//    }

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
            if (dX < 0 && !removed ) {
                Log.println(Log.INFO,null,"$removed  if")
                //swipe right
                activeThreshold = if(prevdx<dX){
                    hideOptions(viewHolder,actionState)
                    activeViewHolder=null
                    rightSwipeThreshold
                } else //swipe left
                    leftSwipeThreshold
                prevdx=dX;
                getDefaultUIUtil().onDraw(
                    c, recyclerView, viewHolder.foreground, dX / 3, dY,
                    actionState, isCurrentlyActive)
            }
            else{
                Log.println(Log.INFO,null,"$removed else")

                super.onChildDraw(c, recyclerView, myViewHolder, dX, dY, actionState, isCurrentlyActive)
            }

    }
//    override fun onChildDrawOver(
//        c: Canvas,
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder?,
//        dX: Float,
//        dY: Float,
//        actionState: Int,
//        isCurrentlyActive: Boolean
//    ) {
//             val myViewHolder: ExerciseViewHolder = viewHolder as ExerciseViewHolder
//            if (dX < 0 && !removed) {
//                if(activeViewHolder!=viewHolder){
//                    refreshView()
//                    activeViewHolder=viewHolder
//                }
//
//                if(prevdx < dX){
//                    activeThreshold=leftSwipeThreshold
//                }
//                prevdx=dX
//                activeThreshold=rightSwipeThreshold
//                getDefaultUIUtil().onDrawOver(
//                    c, recyclerView, viewHolder.foreground, dX / 3, dY,
//                    actionState, isCurrentlyActive
//                )
//            } else{
//                super.onChildDrawOver(c, recyclerView, myViewHolder, dX, dY, actionState, isCurrentlyActive)
//
//            }
//    }
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
        Log.println(Log.INFO,null,"Clicked $clicked_x  $clicked_y  "+activeViewHolder?.nameTextView?.text)

        if(p1.action==MotionEvent.ACTION_DOWN ) {
            var temp:Int?=activeViewHolder?.editButtonClicked(clicked_x,clicked_y)
            Log.println(Log.INFO,null,"Clicked temp: "+temp)
           if(temp!=null && temp>=0) {
                activeViewHolder?.editExercise()
                return true;
            }
             temp=activeViewHolder?.deleteButtonClicked(clicked_x,clicked_y)
            Log.println(Log.INFO,null,"Clicked temp2: "+temp)

            if(temp!=null && temp>=0){
               Log.println(Log.INFO,null,"DELETE BUTTON PRESSED! "+activeViewHolder?.nameTextView?.text)
               removed = true;
               adapter.onItemDismiss(temp)
               return true
            }
//            else if(activeViewHolder?.viewClicked(clicked_x,clicked_y) == true){
//                refreshView()
//            }
        }
        return false;

    }

    @SuppressLint("ClickableViewAccessibility")
    fun setListener(recycler: RecyclerView) {
        recycler.setOnTouchListener(this)
    }
    private fun deleteExercise() {

        if (activeViewHolder != null) {
            removed = true;
            adapter.onItemDismiss(activeViewHolder!!.adapterPosition)
        }
    }
//    } fun refreshView(){
//        Log.println(Log.INFO,null,"REFRESHED! $clicked_x  $clicked_y")
//        if(activeViewHolder!=null){
//
//            adapter.notifyItemChanged(activeViewHolder!!.adapterPosition)
//        }
//
//
//        showed=false
//        activeViewHolder=null
//        removed=false;
//
//    }

}