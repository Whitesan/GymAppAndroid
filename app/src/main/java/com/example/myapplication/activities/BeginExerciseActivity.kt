package com.example.myapplication.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.Stopwatch
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Series
import com.example.myapplication.exercises.Training

class BeginExerciseActivity : AppWindowActivity() {
    private lateinit var actualExercise: Exercise
    private lateinit var actualSet: Series
    private lateinit var todayTraining: Training
    companion object{
         var actualExerciseIndex: Int = 0
         var actualSetIndex: Int = 0

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin_exercise)
        startClock(null)

        getTraining()
        setRepsAndWeight()
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setOnClickListener {
            onBackPressed()
        }

        val doneButton = findViewById<Button>(R.id.doneButton)
        doneButton.doneListener()

    }
    private fun getTraining(){
        todayTraining = super.getIntent().getSerializableExtra("todayTraining") as Training
        actualExercise = todayTraining.getExercises()[0]
        todayTraining.getCurrentExerciseAndSet()

    }
    private fun setRepsAndWeight(){
        val name:TextView = findViewById(R.id.exerciseName)
        name.text = actualExercise.getName()

        val reps:TextView= findViewById(R.id.reps)
        reps.text = actualSet.reps.toString()

        val weight:TextView= findViewById(R.id.weight)
        weight.text = actualSet.weight.toString()
        if(actualExercise.getPart()!!.isCardio()){
            val weightDesc:TextView = findViewById(R.id.weightDesc)
            weightDesc.text = getString(R.string.meters)
        }
    }
    override fun onBackPressed() {
        showAlert()
    }
    private fun backToMenu(){
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)
    }
    private fun Button.doneListener(){
        setOnClickListener {

        intent = Intent(applicationContext, TrainingActivity::class.java)
        intent.putExtra("EditTraining",todayTraining)
        intent.putExtra("Exercise", actualExercise)
        intent.putExtra("Series", actualSet)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)        }

    }

    private fun startClock(descStartTime: Int?) {
        val clock: TextView = findViewById(R.id.clock)
        Stopwatch(0,null)
            .start(100,clock)
    }
    private fun Training.getCurrentExerciseAndSet(){
        if(actualExerciseIndex < getExercises().size){
            actualExercise=getExercises()[actualExerciseIndex]
        }
        if(actualSetIndex < actualExercise.list.size){
            actualSet = actualExercise.list[actualSetIndex++]
        }else{
            actualSetIndex=0
            actualExerciseIndex++
            return  getCurrentExerciseAndSet()
        }
    }
    fun showAlert(){
        val title = resources.getString(R.string.goBack)
        val yes = resources.getString(R.string.TLA_YES)
        val no = resources.getString(R.string.TLA_NO)
       val builder = AlertDialog.Builder(this, R.style.AlertDialog)
        builder.setTitle(title)
        builder.setMessage(resources.getString(R.string.mess))

        builder.setPositiveButton(yes) { _, _ ->
            backToMenu()
        }

        builder.setNegativeButton(no) { _, _ ->
            return@setNegativeButton
        }
        builder.show()
    }

}