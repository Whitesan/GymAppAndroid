package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.myapplication.Constants.Companion.MAX_REPS_PERCENTAGE
import com.example.myapplication.Constants.Companion.PARTS_PER_LIST
import com.example.myapplication.Constants.Companion.TRAINING_FILE
import com.example.myapplication.R
import com.example.myapplication.Stopwatch
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Part
import com.example.myapplication.exercises.Training
import com.google.gson.Gson
import java.io.Serializable
import java.util.*
import kotlin.concurrent.timerTask


//TODO import training list from json, according to week day

/*
TODO     layout
1.actual part and exercise
2.clock + start/stop button
3.show planned reps and weight  + enter done reps and weight
4.skip/end series (?)



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
    private var todayTraining: Training? = null
    private var actualExerciseIndex: Int = 0
    private lateinit var actualExercise: Exercise
    private var firstWindow = true
    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setOnClickListener { onBackPressed() }

        getTraining(super.getIntent().getSerializableExtra("Training"))
        Log.i("training", (todayTraining != null).toString())

        if (todayTraining != null && firstWindow) {  //first show training info
            Log.i("info", "test")
            openTrainingInfo()
        } else if (!firstWindow) {
            openTrainingWindow()
        }
    }

    private fun getTraining(serializable: Serializable?) {
        if (serializable != null) {
            todayTraining = serializable as Training
            firstWindow = false
        } else {
            val trainings = TrainingJsonConverter.loadTrainingJson("$filesDir/$TRAINING_FILE")
            todayTraining = trainings.getTrainingByDay(null)
        }
        if (todayTraining != null) {
            val title: TextView = findViewById(R.id.TitleTrainingName)
            title.text = todayTraining?.getName()
            actualExercise = (todayTraining as Training).getExercises()[actualExerciseIndex++]
        } else {
            showError()
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
            openTrainingWindow()
        }
        listView.divider = null
    }

    private fun openTrainingInfo() {
        val cardView: CardView = findViewById(R.id.TrainingInfoCard)
        val exerciseInfo: LinearLayout = findViewById(R.id.TrainingInfoLayout)
        exerciseInfo.visibility = View.VISIBLE

        val animationLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left_animation)
        cardView.setOnClickListener {
            openTrainingWindow()
        }
        setListView(todayTraining!!)
        animationLeft.onStartShowButton(this)
        exerciseInfo.startAnimation(animationLeft)


    }

    private fun Animation.onStartShowButton(context: Context) {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                val button: RelativeLayout = findViewById(R.id.selectAnotherExerciseLayout)
                selectAnotherButton = findViewById(R.id.selectAnotherExerciseButton)
                selectAnotherButton.visibility = View.VISIBLE
                selectAnotherButton.setOnClickListener {
                    startActivity(
                        Intent(
                            applicationContext,
                            SelectAnotherTrainingActivity::class.java
                        )
                    )
                    overridePendingTransition(R.anim.fade_in_animation, R.anim.slide_out_top)
                }
                val animationDown =
                    AnimationUtils.loadAnimation(context, R.anim.slide_in_left_animation)
                button.startAnimation(animationDown)

            }

            override fun onAnimationEnd(p0: Animation?) {}
            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    private fun Animation.onStartHideButton(context: Context, cardView: View) {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                val button: RelativeLayout = findViewById(R.id.selectAnotherExerciseLayout)
                selectAnotherButton = findViewById(R.id.selectAnotherExerciseButton)
                selectAnotherButton.visibility = View.GONE
                val animationDown =
                    AnimationUtils.loadAnimation(context, R.anim.slide_out_left_animation)
                button.startAnimation(animationDown)
            }

            override fun onAnimationEnd(p0: Animation?) {
                cardView.visibility = View.GONE

            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    private fun Animation.setLabelAnimListener(context: Context) {
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                val title: TextView = findViewById(R.id.navBarTitle)
                val animIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_top)

                val animOut = AnimationUtils.loadAnimation(context, R.anim.slide_out_top)
                animOut.duration = animOut.duration / 2
                animOut.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationEnd(p0: Animation?) {
                        title.text = todayTraining?.getName()
                        animIn.duration = animIn.duration / 2
                        title.startAnimation(animIn)
                        title.isSelected = true

                    }

                    override fun onAnimationStart(p0: Animation?) {}
                    override fun onAnimationRepeat(p0: Animation?) {}
                })
                if (firstWindow) {
                    title.startAnimation(animOut)

                } else {
                    title.text = todayTraining?.getName()
                    title.startAnimation(animIn)
                }
                title.isSelected = true

            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationRepeat(p0: Animation?) {}
        })
    }

    private fun hideCardView() {
        val cardView: CardView = findViewById(R.id.TrainingInfoCard)
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right_animation)
        animation.onStartHideButton(this, cardView)
        cardView.startAnimation(animation)

    }

    private fun showError() {
        val cardView: CardView = findViewById(R.id.TrainingInfoCard)
        val anim = AnimationUtils.loadAnimation(this, R.anim.slide_in_left_animation)
        anim.onStartShowButton(this)
        cardView.startAnimation(anim)
        val trainingInfoLayout: LinearLayout = findViewById(R.id.TrainingInfoLayout)
        val size = trainingInfoLayout.layoutParams
        val errorLayout: LinearLayout = findViewById(R.id.errorLayout)
        if (errorLayout.layoutParams.height < size.height)
            errorLayout.layoutParams = size
        errorLayout.visibility = View.VISIBLE
        errorLayout.setOnClickListener {
            onBackPressed()
        }
    }

    private fun openTrainingWindow() {
        hideCardView()
        val screen: LinearLayout = findViewById(R.id.TrainingScreen)
        val animIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom_animation)
        animIn.setLabelAnimListener(this)


        screen.visibility = View.VISIBLE
        screen.startAnimation(animIn)
        val actualExerciseView: TextView = findViewById(R.id.actualExercise)
        actualExerciseView.text = actualExercise.getName()
        actualExerciseView.isSelected = true
        val actualPartView: TextView = findViewById(R.id.actualPart)
        actualPartView.text = this.getText((actualExercise.getPart() as Part).getStringId())
        initNumberPickers()

        val beginButton: Button = findViewById(R.id.beginButton)
        beginButton.setOnClickListener {
            startActivity(Intent(applicationContext, BeginExerciseActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation, R.anim.slide_out_left_animation)
        }



        startClock(null)
    }

    override fun onBackPressed() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in_animation, R.anim.slide_out_right_animation)
    }

    private fun initNumberPickers() {
        val weightPicker: NumberPicker = findViewById(R.id.weightPicker)
        weightPicker.minValue = 0
        weightPicker.maxValue = MAX_REPS_PERCENTAGE * actualExercise.list[0].reps
        weightPicker.value = actualExercise.list[0].reps
        val repsPicker: NumberPicker = findViewById(R.id.repsPicker)
        repsPicker.minValue = 0
        repsPicker.maxValue = MAX_REPS_PERCENTAGE * actualExercise.list[0].weight
        repsPicker.value = actualExercise.list[0].weight
    }

    private fun startClock(descStartTime: Int?) {
        val clock: TextView = findViewById(R.id.clock)
        Stopwatch(0,null)
            .start(100,clock)
    }

    fun deepCopy(): Training {
        val json = Gson().toJson(this)
        return Gson().fromJson(json, Training::class.java)
    }
}