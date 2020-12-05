package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.ListAdapter
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Part
import com.example.myapplication.recycler_view.AddButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("DEPRECATION")
open class CreateTrainingActivity : AppWindowActivity() {
    var enteredText:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_training)
        val button = findViewById<ImageView>(R.id.backTrainingCreator)
        button.setOnClickListener{
            val intent =  Intent(applicationContext, PlannerActivity::class.java)
            startActivity(intent)
        }
        setTextListener(1)

        val list= ListElements()
        val adapter=createRecyclerView(list)

        val newExerciseButton = findViewById<FloatingActionButton>(R.id.createExerciseButton)
        newExerciseButton.setOnClickListener{
            updateRecyclerView(list, adapter)
        }
        val endButton=findViewById<Button>(R.id.endCreatingExercises)
        endButton.setOnClickListener{
            if (enteredText.isEmpty()) {
                showErrorMessage()
            } else {
                val intent =  Intent(applicationContext, PlannerActivity::class.java)
                startActivity(intent)

            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun setTextListener(number:Int) {
        val entry = findViewById<EditText>(R.id.enterTrainingName)
        entry.setText(getString(R.string.defaultName)+number.toString())
        entry.setOnFocusChangeListener{ v, focus ->
            if(focus==false){
                enteredText = entry.text.toString()
                entry.hideKeyboard()
            }
            else if(entry.getText().toString().contains(getString(R.string.defaultName))){
                entry.setText("")
            }
            entry.isCursorVisible = focus

        }
        entry.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == EditorInfo.IME_ACTION_DONE || keyCode == EditorInfo.IME_ACTION_SEARCH) {
                enteredText = entry.text.toString()
                entry.hideKeyboard()
                entry.isCursorVisible = false
                return@OnKeyListener true
            }
            false
        })
    }

    private fun createRecyclerView(list: ListElements): ListAdapter<Exercise> {
        val recycler=findViewById<RecyclerView>(R.id.recyclerView)
        val adapter= ListAdapter<Exercise>(list)
        recycler.adapter=adapter
        recycler.layoutManager=LinearLayoutManager(this)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recycler)
        adapter.notifyItemInserted(list.addButton(AddButton()))
        return adapter
    }
    var id=0
    private fun updateRecyclerView(elementList: ListElements, adapter: ListAdapter<Exercise>){
        var temp= Exercise("Nazwa # $id", Part.getPart("Plecy")!!);
        id++
        val index=elementList.appendList(temp)
        adapter.notifyItemInserted(index)
        val recycler=findViewById<RecyclerView>(R.id.recyclerView)
        recycler.scrollToPosition(index+1)
    }
    private fun showErrorMessage() {
        val message = findViewById<TextView>(R.id.EnterTraining2)
        message.text=getString(R.string.empty_name)
        message.setTextColor(resources.getColor(R.color.red))
    }
}