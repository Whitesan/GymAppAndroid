package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.size
import com.example.myapplication.Constants.Companion.PARTS_PER_LIST
import com.example.myapplication.Constants.Companion.TRAINING_FILE
import com.example.myapplication.R
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Training
import java.io.Serializable
import java.util.*


//TODO import training list from json, according to week day

/*
TODO     layout
1.actual part and exercise
2.clock + start/stop button
3.show planned reps and weight  + enter done reps and weight
4.skip/end series (?)
5.allow user to change expected training for another (maybe open calendar?)


 */
/*TODO save stats to JSON
1.day
2.exercise
3.predicted and actual  reps + weight
4.(?)time:  length of exercise and length if rest
*/
//TODO On finish training show summary stats and congratulations (or not)
//TODO maybe on finish ask for user weight, if he'd like to share save that for future statistics
//TODO !! Update predicted training according to actual progress (Never regress !!)
@Suppress("DEPRECATION")

class TrainingActivity : AppWindowActivity() {
    private lateinit var selectAnotherButton: Button
    private  var todayTraining: Training? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        val button = findViewById<ImageView>(R.id.backTraining)
        button.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        selectAnotherButton = findViewById(R.id.selectAnotherExerciseButton)
        selectAnotherButton.setOnClickListener {
            val intent = Intent(applicationContext, SelectAnotherTrainingActivity::class.java)
            startActivity(intent)
        }
        cardAnimateOutListener()

        val serializable: Serializable? = super.getIntent().getSerializableExtra("Training")
        todayTraining = if (serializable != null) {
            serializable as Training
        } else {
            val trainings = TrainingJsonConverter.loadTrainingJson("$filesDir/$TRAINING_FILE")
              trainings.getTrainingByDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
//            trainings.getTrainingByDay(null)
        }
        if (todayTraining == null) {
            showError()
        } else {
            val title: TextView = findViewById(R.id.TitleTrainingName)
            title.text = todayTraining?.getName()
            setListView(todayTraining!!)
        }
    }


    private fun setListView(training: Training) {
        val set: MutableSet<String> = linkedSetOf()
        for (exercise: Exercise in training.getExercises()) {
            Log.println(
                Log.INFO,
                null,
                exercise.getPart().toString()
            )
            exercise.getPart()?.getStringId()?.let { set.add(this.getString(it)) }
        }
        val list = set.toTypedArray().toList()
        val size = if (list.size < PARTS_PER_LIST) list.size else PARTS_PER_LIST
        val listView: ListView = findViewById(R.id.ListOfParts)
        listView.adapter = ArrayAdapter(
            this,
            R.layout.list_of_parts, list.subList(0, size)
        )
        val cardView: CardView = findViewById(R.id.TrainingInfoCard)
        listView.setSelector(R.color.transparent)
        listView.setOnItemClickListener { _: AdapterView<*>, _: View, _: Int, _: Long ->
            animateOut()
        }
        listView.divider = null
    }

    private fun cardAnimateOutListener() {
        val cardView: CardView = findViewById(R.id.TrainingInfoCard)
        cardView.setOnClickListener {
            animateOut()
        }
    }

    private fun animateOut() {
        val cardView: CardView = findViewById(R.id.TrainingInfoCard)
        val buttonAnim = AnimationUtils.loadAnimation(this, R.anim.slide_down_animation)
        val anim = AnimationUtils.loadAnimation(this, R.anim.slide_out_animation)
        val titleAnimOut= AnimationUtils.loadAnimation(this, R.anim.slide_out_up)
        val titleAnimIn= AnimationUtils.loadAnimation(this, R.anim.slide_in_from_top)
        val title:TextView=findViewById(R.id.plannerTitle)

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                selectAnotherButton.startAnimation(buttonAnim)
                title.startAnimation(titleAnimOut)
            }

            override fun onAnimationRepeat(p0: Animation?) {
                //                not implemented
            }

            override fun onAnimationEnd(p0: Animation?) {
                val errorLayout: LinearLayout = findViewById(R.id.errorLayout)
                if(errorLayout.visibility == View.VISIBLE){
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    cardView.visibility = View.GONE
                    selectAnotherButton.visibility = View.GONE
                    title.text = todayTraining?.getName()
                    title.startAnimation(titleAnimIn)
                }


            }
        })
        cardView.startAnimation(anim)
    }

    private fun showError() {
        val trainingInfoLayout: LinearLayout = findViewById(R.id.TrainingInfoLayout)
        val  size= trainingInfoLayout.layoutParams
        trainingInfoLayout.visibility = View.GONE
        val errorLayout: LinearLayout = findViewById(R.id.errorLayout)
        if(errorLayout.layoutParams.height < size.height)
            errorLayout.layoutParams = size
        errorLayout.visibility = View.VISIBLE
    }

}