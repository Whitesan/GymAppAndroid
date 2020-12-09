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
    private lateinit var part:Part
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
                showErrorMessage()
            } else {
                val exercise =Exercise(enteredText, part)
                val intent = Intent(applicationContext, ExerciseActivity::class.java)
                intent.putExtra("Exercise", exercise)
                startActivity(intent)
            }
        } 

        val typePicker = findViewById<LinearLayout>(R.id.typePicker)
        //Set on click listener to containers
        for(i in typePicker)
        {
            i.alpha = 0.5F
            i.setOnClickListener{
                //Set off all containers
                for(j in typePicker)
                {
                    if(j != i)
                    {
                        j.alpha = 0.5F
                    }
                }
                //Set on clicked container and set current Window to i
                i.alpha = 1F
                selectedTypeView = i as LinearLayout

                val textField =i.getChildAt(0) as TextView
                part = Part.getPart(textField.text.toString()) !!

            }
        }
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
}