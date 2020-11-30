package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class ExerciseActivity : AppCompatActivity() {
    val exercise = Exercise()
    var seriesCounter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_activity)




        var name:String?
        val entry = findViewById<EditText>(R.id.enterExerciseName)
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


        val weightPicker = findViewById<NumberPicker>(R.id.weightPicker)
        weightPicker.maxValue = 500
        weightPicker.minValue = 0

        val repsPicker = findViewById<NumberPicker>(R.id.repsPicker)
        repsPicker.maxValue = 100
        repsPicker.minValue = 0


        val button = findViewById<ImageView>(R.id.backExercise)
        button.setOnClickListener{
            val intent =  Intent(applicationContext, CreateTrainingActivity::class.java)
            startActivity(intent)
        }

        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        params.setMargins(10,10,10,10)

        val seriesPicker = findViewById<LinearLayout>(R.id.seriesPicker)


        val addExercise = findViewById<Button>(R.id.addExercise)
        addExercise.setOnClickListener{
            seriesCounter++;
            val button = Button(this) as Button;
            button.text = seriesCounter.toString()

            button.setBackgroundColor(button.context.resources.getColor(R.color.green))
            button.layoutParams = params
            seriesPicker.addView(button)

            val reps = repsPicker.value
            val weight = weightPicker.value
            val exerciseNameField = findViewById<EditText>(R.id.enterExerciseName)
            val exerciseName = exerciseNameField.text
            val series = Series(seriesCounter,reps,weight)
            exercise.addSeries(series)
            Toast.makeText(this,series.toString(),Toast.LENGTH_LONG).show()

        }



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
    fun View.hideKeyboard(){
        hideSystemUI()
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}