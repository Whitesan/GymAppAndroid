package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Part

@Suppress("DEPRECATION")
class EnterExerciseNameActivity : AppWindowActivity() {
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
/*
        val typePicker = findViewById<LinearLayout>(R.id.typePicker)
        val but = createSeriesButton("   view1111111  ")
        val but2 = createSeriesButton("   view2222222   ")
        val but3 = createSeriesButton("   view3333333   ")
        val but4 = createSeriesButton("view4")
        val but5 = createSeriesButton("view5")
        typePicker.addView(but)
        typePicker.addView(but2)
        typePicker.addView(but3)
        typePicker.addView(but4)
        typePicker.addView(but5)
        // typePicker.addView(but)*/

    }

    fun createSeriesButton(text: String): Button {
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(10, 10, 10, 10)

        val button = Button(this)


        button.text = text

        button.layoutParams = params
        button.setBackgroundDrawable(getResources().getDrawable(R.color.light_gray));
        return button;
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


    fun View.hideKeyboard() {
        super.hideSystemUI()
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}