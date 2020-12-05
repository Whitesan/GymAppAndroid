package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Part

@Suppress("DEPRECATION")
class EnterExerciseNameActivity : AppCompatActivity() {
    private var enteredText: String = ""
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
                val exercise = Part.getPart("plecy")?.let { part -> Exercise(enteredText, part) }
                val intent = Intent(applicationContext, ExerciseActivity::class.java)
                intent.putExtra("Exercise", exercise)
                startActivity(intent)
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
        message.setVisibility(View.VISIBLE);
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

    fun View.hideKeyboard() {
        hideSystemUI()
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}