package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.*
import com.example.myapplication.Constants.Companion.TRAINING_FILE
import com.example.myapplication.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Training
import com.example.myapplication.exercises.Trainings
import com.example.myapplication.recycler_view.AddButton
import com.example.myapplication.recycler_view.SimpleItemTouchHelperCallback
import java.io.Serializable
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class CreateTrainingActivity : AppWindowActivity(), View.OnTouchListener {
    companion object {
        var exerciseList = ArrayList<Exercise>()
        var enteredText: String = ""
        var editedIndex = -1
    }

    private val adapter = ListAdapter(this, exerciseList)
    private lateinit var entry: EditText
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Material_Light)
        super.onCreate(savedInstanceState)
        adapter.setHasStableIds(true)
        setContentView(R.layout.activity_create_training)
        entry = findViewById<EditText>(R.id.enterTrainingName)


        val temp: Serializable? = super.getIntent().getSerializableExtra("Exercise")
        if (temp != null) {
            exerciseList.add(temp as Exercise)
        }

        val temp2: Serializable? = super.getIntent().getSerializableExtra("editedExercise")
        if (temp2 != null) {
            for (i in 0 until exerciseList.size) {
                if (exerciseList[i].getId() == editedIndex) {
                    exerciseList.removeAt(i)
                    editedIndex = -1
                    if (i > 0)
                        exerciseList.add(i, temp2 as Exercise)
                    else
                        exerciseList.add(i, temp2 as Exercise)
                    break;
                }
            }
        }


        setTextListener(1)
        createRecyclerView()

        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setOnClickListener {
            addButtonAction()
        }

        val endButton = findViewById<Button>(R.id.endCreatingExercises)
        endButton.setOnClickListener {
            saveButtonAction()
        }
    }

    private fun saveButtonAction() {
        if (enteredText.isEmpty()) {
            showErrorMessage()
        }
        else if(exerciseList.isEmpty()){
            errorEmptyTraining()
        }

        else {
            onExit()
        }
    }

    private fun addButtonAction() {
        val intent = Intent(applicationContext, PlannerActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun setTextListener(number: Int) {
        if (enteredText.isNotEmpty())
            entry.setText(enteredText)

        entry.setOnFocusChangeListener { _, focus ->
            if (focus == false) {
                enteredText = entry.text.toString()
                entry.hideKeyboard()
            }
            entry.isCursorVisible = focus
        }
        entry.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == EditorInfo.IME_ACTION_DONE || keyCode == EditorInfo.IME_ACTION_SEARCH) {
                enteredText = entry.text.toString()
                entry.hideKeyboard()
                entry.isCursorVisible = false
                return@OnKeyListener true
            }
            false
        })

    }

    private fun createRecyclerView() {
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
        val callback = SimpleItemTouchHelperCallback(adapter, recycler)
        callback.setListener(recycler)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recycler)
//        callback.setItemTouchHelper(itemTouchHelpter)
        adapter.appendItem(AddButton(), recycler)
        for (e: Exercise in exerciseList) {
            adapter.appendItem(e, recycler)
        }
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        enteredText = entry.text.toString()
        val intent = Intent(applicationContext, EnterExerciseNameActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in_animation,R.anim.fade_out_animation)

        return false;
    }

    private fun trainingWithNameExistDialog(trainings :Trainings, path:String) {
        val context = this
        val builder = AlertDialog.Builder(context, R.style.AlertDialog)
        val title = getString(R.string.CTA_Title_Dialog)
        val msg1 = getString(R.string.CTA_Msg_1_Dialog)
        val msg2 = getString(R.string.CTA_Msg_2_Dialog)
        val yes = getString(R.string.CTA_YES)
        val no = getString(R.string.CTA_NO)


        builder.setTitle(title)
        builder.setMessage("$msg1 $enteredText $msg2")

        builder.setPositiveButton(yes) { dialog, which ->
            val list = trainings.trainingList
            for(i in list.indices){
                if(enteredText == list[i].getName()){
                    list[i].setExercises(exerciseList)
                    break
                }
            }
            val json: TrainingJsonConverter = TrainingJsonConverter()
            json.toJson(trainings, path)

            enteredText = ""
            exerciseList.clear()
            val intent = Intent(applicationContext, TrainingsListActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton(no) { dialog, which ->
            return@setNegativeButton
        }
        builder.show()
    }

    private fun onExit() {
        val training = Training(enteredText, exerciseList)
        val yourFilePath = "$filesDir/$TRAINING_FILE"

        val json: TrainingJsonConverter = TrainingJsonConverter()
        var trainings: Trainings? = json.fromJson(yourFilePath)
        if (trainings == null)
            trainings = Trainings(ArrayList())

        for(t in trainings.trainingList){
            if(t.getName() == enteredText){
                trainingWithNameExistDialog(trainings, yourFilePath)
                return
            }
        }

        trainings.trainingList.add(training)
        json.toJson(trainings, yourFilePath)

        enteredText = ""
        exerciseList.clear()
        val intent = Intent(applicationContext, TrainingsListActivity::class.java)
        startActivity(intent)
    }

    private fun showErrorMessage() {
        val message = findViewById<TextView>(R.id.EnterTraining2)
        message.text = getString(R.string.CTA_Empty_Name_Err)
        message.setTextColor(resources.getColor(R.color.red))
    }
    private fun errorEmptyTraining() {
        val message = findViewById<TextView>(R.id.EnterTraining2)
        message.text = getString(R.string.CTA_Empty_Training_Err)
        message.setTextColor(resources.getColor(R.color.red))
        val highlight: Animation = AnimationUtils.loadAnimation(this, R.anim.highlight_animation)
        val recyclerButton: CardView = findViewById(R.id.listButtonElementLayout)
        recyclerButton.startAnimation(highlight)

    }
}