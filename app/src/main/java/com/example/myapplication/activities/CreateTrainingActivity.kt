package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
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
 class CreateTrainingActivity : AppWindowActivity(), View.OnTouchListener {
    var enteredText:String=""
    private val list= ListElements()
    private val adapter= ListAdapter<Exercise>(list,this)
    private var id=0  //TODO delete this later
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_training)
        setTextListener(1)
        createRecyclerView()
        val button = findViewById<ImageView>(R.id.backTrainingCreator)
        button.setOnClickListener{
            val intent =  Intent(applicationContext, PlannerActivity::class.java)
            startActivity(intent)
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
        enteredText= entry.text.toString()
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

    private fun createRecyclerView(){
        val recycler=findViewById<RecyclerView>(R.id.recyclerView)
        recycler.adapter=adapter
        recycler.layoutManager=LinearLayoutManager(this)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recycler)
        adapter.notifyItemInserted(list.addButton(AddButton()))
    }

    private fun updateRecyclerView(){
        val temp= Exercise("Nazwa # $id", Part.getPart("Plecy")!!);
        id++
        val index=list.appendList(temp)
        adapter.notifyItemInserted(index)
        val recycler=findViewById<RecyclerView>(R.id.recyclerView)
        recycler.scrollToPosition(index+1)
    }
    private fun showErrorMessage() {
        val message = findViewById<TextView>(R.id.EnterTraining2)
        message.text=getString(R.string.empty_name)
        message.setTextColor(resources.getColor(R.color.red))
    }
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        updateRecyclerView()
        return false;
    }
}