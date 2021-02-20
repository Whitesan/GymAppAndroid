package com.example.myapplication.activities

import android.app.AlertDialog
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
import com.example.myapplication.exercises.Series
import com.example.myapplication.exercises.Training
import com.google.gson.Gson
import java.util.*



@Suppress("DEPRECATION")

class TrainingActivity : AppWindowActivity() {
    companion object {
        var firstOpen = true
        var finish = false
        var firstWindow = true
    }

    private var actualExercise: Exercise? = null
    private lateinit var actualSet: Series
    private lateinit var selectAnotherButton: Button
    private var todayTraining: Training? = null
    private var enableBack = false
    private var firstWindow = true
    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setOnClickListener {
            onBackPressed()
        }

        getTraining()
        if (finish) {
            enableBack = true
            trainingFinished()
//            finish = false
        } else
            if (todayTraining != null && firstWindow) {
                enableBack = true
                openTrainingInfo()
            } else if (firstOpen && todayTraining != null) {
                firstOpen = false
                openBeginExerciseActivity()
            } else if (!firstWindow) {
                openTrainingWindow()
            }
    }

    private fun getTraining() {
        val trainingFromSelectAnother = super.getIntent().getSerializableExtra("Training")
        val editTrainingCopy = super.getIntent().getSerializableExtra("EditTraining")
        when {
            trainingFromSelectAnother != null -> {
                todayTraining = trainingFromSelectAnother as Training
                firstWindow = false
                firstOpen = true
                finish = false
                BeginExerciseActivity.actualSetIndex = 0
                BeginExerciseActivity.actualExerciseIndex = 0
            }
            editTrainingCopy != null -> {
                todayTraining = editTrainingCopy as Training
                firstWindow = false
                actualExercise = super.getIntent().getSerializableExtra("Exercise") as Exercise
                actualSet = super.getIntent().getSerializableExtra("Series") as Series

            }
            else -> {
                if (finish) {
                    return
                }
                finish = false
                val trainings = TrainingJsonConverter.loadTrainingJson("$filesDir/$TRAINING_FILE")
                var todayDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2
                if (todayDay < 0) todayDay = 6
                todayTraining =
                    trainings.getTrainingByDay(todayDay)
                        ?.deepCopy()
                firstOpen = true
                BeginExerciseActivity.actualSetIndex = 0
                BeginExerciseActivity.actualExerciseIndex = 0
            }
        }
        if (todayTraining != null) {
            val title: TextView = findViewById(R.id.TitleTrainingName)
            title.text = todayTraining?.getName()
            finish = false
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
            firstOpen = false
            openBeginExerciseActivity()
        }
        listView.divider = null
    }

    private fun openTrainingInfo() {
        val cardView: CardView = findViewById(R.id.TrainingInfoCard)
        val exerciseInfo: LinearLayout = findViewById(R.id.TrainingInfoLayout)
        exerciseInfo.visibility = View.VISIBLE

        val animationLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left_animation)
        cardView.setOnClickListener {
            firstOpen = false
            openBeginExerciseActivity()
        }
        if (todayTraining != null) {
            setListView(todayTraining!!)

        }
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
            backToMenu()
        }
    }

    private fun openTrainingWindow() {
        val beginButton: Button = findViewById(R.id.beginButton)
        beginButton.beginExerciseListener()
        hideCardView()
        val screen: LinearLayout = findViewById(R.id.TrainingScreen)
        val animIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom_animation)
        animIn.setLabelAnimListener(this)
        setSeriesText()

        screen.visibility = View.VISIBLE
        screen.startAnimation(animIn)
        val actualExerciseView: TextView = findViewById(R.id.actualExercise)
        actualExerciseView.text = actualExercise?.getName()
        actualExerciseView.isSelected = true
        val actualPartView: TextView = findViewById(R.id.actualPart)
        actualPartView.text = this.getText((actualExercise?.getPart() as Part).getStringId())
        initNumberPickers()
        startClock(null)
        if (!firstOpen && BeginExerciseActivity.actualExerciseIndex + 1 >= todayTraining!!.getExercises().size
            && BeginExerciseActivity.actualSetIndex >= actualExercise!!.list.size
        ) {
            finish = true
            beginButton.text = getText(R.string.finish)
        }
    }

    private fun openBeginExerciseActivity() {
        if (actualExercise == null)
            actualExercise = todayTraining!!.getExercises()[0]
        if (finish) {
            val intent = Intent(applicationContext, TrainingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_animation, R.anim.slide_out_left_animation)
        } else {
            Log.i("TRAINING", "${todayTraining != null}")
            val intent = Intent(applicationContext, BeginExerciseActivity::class.java)
            intent.putExtra("todayTraining", todayTraining)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_animation, R.anim.slide_out_left_animation)
        }
    }

    private fun Button.beginExerciseListener() {
        setOnClickListener {
            openBeginExerciseActivity()
        }
    }

    override fun onBackPressed() {
        if (!enableBack) {
            showAlert()
        } else {

            backToMenu()
        }
    }

    private fun initNumberPickers() {
        val weightPicker: NumberPicker = findViewById(R.id.weightPicker)
        weightPicker.minValue = 0
        weightPicker.maxValue = MAX_REPS_PERCENTAGE * actualExercise!!.list[0].weight
        weightPicker.value = actualSet.reps
        val repsPicker: NumberPicker = findViewById(R.id.repsPicker)
        repsPicker.minValue = 0
        repsPicker.maxValue = MAX_REPS_PERCENTAGE * actualExercise!!.list[0].reps
        repsPicker.value = actualSet.weight
    }

    private fun startClock(descStartTime: Int?) {
        val clock: TextView = findViewById(R.id.clock)
        Stopwatch(0, null)
            .start(100, clock)
    }

    private fun Training.deepCopy(): Training {
        val json = Gson().toJson(this)
        return Gson().fromJson(json, Training::class.java)
    }

    private fun setSeriesText() {
        val seriesNumber: TextView = findViewById(R.id.seriesNumber)
        val str =
            "${BeginExerciseActivity.actualSetIndex} ${resources.getString(R.string.of)} ${actualExercise!!.list.size}"
        seriesNumber.text = str
        if (actualExercise!!.getPart()!!.isCardio()) {
            val weightDesc: TextView = findViewById(R.id.weightDesc)
            weightDesc.text = getText(R.string.meters)
        }

    }

    private fun trainingFinished() {
//        finish = false
        showError()
        val title: TextView = findViewById(R.id.ErrorTitle)
        title.text = resources.getString(R.string.congratulations)
        val body: TextView = findViewById(R.id.ErrorBody)
        body.text = resources.getString(R.string.trainingFinished)
        todayTraining = null
    }

    private fun showAlert() {
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

    private fun backToMenu() {
        finish = false
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in_animation, R.anim.slide_out_right_animation)
    }
}