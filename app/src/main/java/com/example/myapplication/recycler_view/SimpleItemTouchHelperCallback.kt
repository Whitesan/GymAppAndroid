package com.example.myapplication.recycler_view

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ListAdapter


class SimpleItemTouchHelperCallback(
    private var adapter: ListAdapter, private var recycler: RecyclerView
) : View.OnTouchListener,
    ItemTouchHelper.Callback() {

    private var prevdx: Float = 0f
    private val leftSwipeThreshold = 0.9f
    private val rightSwipeThreshold = 0.6f
    private var activeThreshold = leftSwipeThreshold
    private lateinit var itemTouchHelper: ItemTouchHelper
    var clicked_x = 0f
    var clicked_y = 0f
    var removed = false
    private val swipedViewsSet: MutableSet<ExerciseViewHolder> = LinkedHashSet()
    private val swipedViewsMap: MutableMap<ExerciseViewHolder, Boolean> = HashMap()

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
        showOptions(viewHolder, ItemTouchHelper.ACTION_STATE_SWIPE)
//        if (swipedViewsSet.size > 0) {
//            refreshView(swipedViewsSet.first())
//            swipedViewsSet.clear()
//        }
        swipedViewsSet.add(viewHolder as ExerciseViewHolder)
        swipedViewsMap[viewHolder] = false

        Log.println(
            Log.INFO,
            null,
            swipedViewsSet.toString()
        )


    }

    override fun onSelectedChanged(vh: RecyclerView.ViewHolder?, actionState: Int) {
        if (vh != null) {
            val viewHolder = vh as ExerciseViewHolder
            getDefaultUIUtil().onSelected(viewHolder.background)
        }
        super.onSelectedChanged(vh, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val vh = viewHolder as ExerciseViewHolder
        getDefaultUIUtil().clearView(vh.background)
        super.clearView(recyclerView, viewHolder)
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


        if (dX < 0 && !myViewHolder.removed) {
//            Log.println(Log.INFO, null, "$removed  if")
            //swipe right
            activeThreshold = if (prevdx < dX) {
                hideOptions(viewHolder, actionState)
                swipedViewsSet.remove(viewHolder)
                rightSwipeThreshold
            } else //swipe left
                leftSwipeThreshold
            prevdx = dX
            getDefaultUIUtil().onDraw(
                c, recyclerView, viewHolder.foreground, dX / 3, dY,
                actionState, isCurrentlyActive
            )
        } else {
            //Log.println(Log.INFO, null, "$removed else")
            super.onChildDraw(c, recyclerView, myViewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val myViewHolder: ExerciseViewHolder = viewHolder as ExerciseViewHolder
        if (dX < 0 && !myViewHolder.removed) {
//            Log.println(Log.INFO, null, "$removed  if")
            //swipe right
            activeThreshold = if (prevdx < dX) {
                hideOptions(viewHolder, actionState)
                swipedViewsSet.remove(viewHolder)
                rightSwipeThreshold
            } else //swipe left
                leftSwipeThreshold
            prevdx = dX
            getDefaultUIUtil().onDrawOver(
                c, recyclerView, viewHolder.foreground, dX / 3, dY,
                actionState, isCurrentlyActive
            )
        } else {
            //Log.println(Log.INFO, null, "$removed else")
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

    override fun onTouch(p0: View?, p1: MotionEvent): Boolean {
        clicked_x = p1.x
        clicked_y = p1.y


        if (p1.action == MotionEvent.ACTION_DOWN) {
            for (activeView in swipedViewsSet) {
                Log.println(
                    Log.INFO,
                    null,
                    "Clicked $clicked_x  $clicked_y  " + activeView.nameTextView.text
                )

                var temp: Int? = activeView.editButtonClicked(clicked_x, clicked_y)
//                Log.println(Log.INFO, null, "Clicked temp: " + temp)
                if (temp != null && temp >= 0) {
                    activeView.editExercise()
                    return true
                }
                temp = activeView.deleteButtonClicked(clicked_x, clicked_y)
//                Log.println(Log.INFO, null, "Clicked temp2: " + temp)
                if (temp != null && temp >= 0) {
                    Log.println(
                        Log.INFO,
                        null,
                        "DELETE BUTTON PRESSED! " + activeView.nameTextView.text
                    )
                    removed = true
                    adapter.onItemDismiss(temp)
                    return true
                }
            }
        }
        return false

    }


    @SuppressLint("ClickableViewAccessibility")
    fun setListener(recycler: RecyclerView) {
        recycler.setOnTouchListener(this)
    }

    fun setItemTouchHelper(itemTouchHelper: ItemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper
    }

    private fun refreshView(viewHolder: ExerciseViewHolder) {
        Log.println(Log.INFO, null, "REFRESHED! $clicked_x  $clicked_y")

//        itemTouchHelper = ItemTouchHelper(this)
////        itemTouchHelper.attachToRecyclerView(null);
//        itemTouchHelper.attachToRecyclerView(recycler)
//
//
//
//        adapter.notifyItemChanged(viewHolder.adapterPosition)
    }

}