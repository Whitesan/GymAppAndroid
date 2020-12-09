package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.SimpleItemTouchHelperCallback
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Training
import com.example.myapplication.exercises.Trainings
import com.example.myapplication.recycler_view.AddButton
import java.io.Serializable


@Suppress("DEPRECATION")
 class CreateTrainingActivity : AppWindowActivity(),View.OnTouchListener,View.OnClickListener {
    companion object{
        var exerciseList= ArrayList<Exercise>()
        private var enteredText:String=""
    }
    private val adapter= ListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_training)
        val temp : Serializable? = super.getIntent().getSerializableExtra("Exercise")
        if (temp != null){
            exerciseList.add(temp as Exercise)
        }
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
               onExit()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun setTextListener(number: Int) {
        val entry = findViewById<EditText>(R.id.enterTrainingName)
        if(enteredText.isNotEmpty())
            entry.setText(enteredText)
        else{
            entry.setText(getString(R.string.defaultName) + number.toString())
            enteredText= entry.text.toString()

        }
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
        adapter.appendItem(AddButton(), recycler)
        for(e : Exercise in exerciseList){
            adapter.appendItem(e, recycler)
        }
    }
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        val intent = Intent(applicationContext, EnterExerciseNameActivity::class.java)
        startActivity(intent)
        return false;
    }
    override fun onClick(p0: View?) {
        val editButton:TextView = findViewById(R.id.editButton)
        editButton.setOnClickListener{


        }
    }
    private fun onExit(){
        //TODO save to json
        val training = Training(enteredText, exerciseList)
        val yourFilePath = filesDir.toString() + "/" + "Training.json"
        val json :TrainingJsonConverter = TrainingJsonConverter()
        var trainings = json.fromJson(yourFilePath)
        if(trainings.trainingList == null)
        {
            trainings = Trainings(ArrayList())
        }
        trainings.trainingList.add(training)
        json.toJson(trainings,yourFilePath)

        enteredText= ""
        exerciseList.clear()
        val intent =  Intent(applicationContext, TrainingsListActivity::class.java)
        startActivity(intent)
    }
    private fun showErrorMessage() {
        val message = findViewById<TextView>(R.id.EnterTraining2)
        message.text=getString(R.string.empty_name)
        message.setTextColor(resources.getColor(R.color.red))
    }

}