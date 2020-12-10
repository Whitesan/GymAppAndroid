package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.iterator
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Part

@Suppress("DEPRECATION")
class EnterExerciseNameActivity : AppWindowActivity() {
    private var enteredText: String = ""
    private var part:Part?=null
    private lateinit var selectedTypeView : LinearLayout // selected type Container
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_exercise_name)

        val backButton = findViewById<ImageView>(R.id.backEnterName)
        backButton.setOnClickListener {
            val intent = Intent(applicationContext, CreateTrainingActivity::class.java)
            startActivity(intent)
        }
        setTextListener()
        val buttonContinue = findViewById<Button>(R.id.ContinueCreatingButton)
        buttonContinue.setOnClickListener {
            if (enteredText.isEmpty()) {
                val message = findViewById<TextView>(R.id.TrainingNameEmpty)
                message.visibility = View.VISIBLE;
            }
            else if(part==null){
                val message = findViewById<TextView>(R.id.TrainingNameEmpty)
                message.text=getString(R.string.selectPartError)
                message.visibility = View.VISIBLE;
            }
            else{
                val exercise =Exercise(enteredText, part,CreateTrainingActivity.exerciseList.size)
                val intent = Intent(applicationContext, ExerciseActivity::class.java)
                intent.putExtra("Exercise", exercise)
                startActivity(intent)
            }
        }
        selectPartListener()

    }

    private fun setTextListener() {
        val entry = findViewById<EditText>(R.id.enterExerciseNameEntry)
        entry.setOnFocusChangeListener { v, focus ->
            if (focus == false) {
                enteredText = entry.text.toString()
                entry.hideKeyboard()
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

    private fun showErrorMessage() {
        val message = findViewById<TextView>(R.id.TrainingNameEmpty)
        message.visibility = View.VISIBLE;
    }
    private fun selectPartListener(){
        val typePicker = findViewById<LinearLayout>(R.id.typePicker)
        for(i in typePicker)
        {
            i.alpha = 0.3F
            i.setOnClickListener{
                for(j in typePicker)
                {
                    if(j != i)
                        j.alpha = 0.3F
                }
                i.alpha = 1F
                selectedTypeView = i as LinearLayout
                val textField =i.getChildAt(0) as TextView
                part = Part.getPart(textField.text.toString()) !!

            }
        }
    }
}