package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
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
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("DEPRECATION")
class CreateTrainingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_training)
        val button = findViewById<ImageView>(R.id.backTrainingCreator)
        button.setOnClickListener{
            val intent =  Intent(applicationContext, PlannerActivity::class.java)
            startActivity(intent)
        }
        var name:String?
        val entry = findViewById<EditText>(R.id.enterTrainingName)
        entry.setOnFocusChangeListener{ v, focus ->
                if(focus==false){
                    name = entry.text.toString()
                    entry.hideKeyboard()
                }
                entry.isCursorVisible = focus

        }
        entry.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == EditorInfo.IME_ACTION_DONE || keyCode == EditorInfo.IME_ACTION_SEARCH) {
                name = entry.text.toString()
                entry.hideKeyboard()
                entry.isCursorVisible = false
                return@OnKeyListener true
            }
            false
        })
        val list= ListElements()
        val adapter=createRecyclerView(list)
        val newExerciseButton = findViewById<FloatingActionButton>(R.id.createExerciseButton)
        newExerciseButton.setOnClickListener{
            updateRecyclerView(list, adapter)
        }
        val endButton=findViewById<Button>(R.id.endCreatingExercises)
        endButton.setOnClickListener{
            val intent =  Intent(applicationContext, PlannerActivity::class.java)
            startActivity(intent)
        }


    }
    fun View.hideKeyboard(){
            hideSystemUI()
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        val decorView: View = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }
    private fun createRecyclerView(list: ListElements): ListAdapter<Exercise> {
        val recycler=findViewById<RecyclerView>(R.id.recyclerView)
        val adapter= ListAdapter<Exercise>(list)
        recycler.adapter=adapter
        recycler.layoutManager=LinearLayoutManager(this)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recycler);
        return adapter
    }
    var id=0
    fun updateRecyclerView(elementList: ListElements, adapter: ListAdapter<Exercise>){
        var temp= Exercise("Nazwa # $id", Part.getPart("Plecy")!!);
        id++
        adapter.notifyItemInserted(elementList.appendList(temp))
    }

}