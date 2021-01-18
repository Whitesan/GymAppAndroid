
package com.example.myapplication.activities
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.HorizontalScrollView
import android.widget.NumberPicker.OnValueChangeListener
import androidx.core.view.get
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Series
import java.io.Serializable


@Suppress("DEPRECATION")
class ExerciseActivity : AppWindowActivity() {

    lateinit var exercise : Exercise
    var seriesCounter = 1
    var selectedSeries = 1
    private var edit = false
    private var ready = false


    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        var serializable : Serializable? = super.getIntent().getSerializableExtra("Exercise")
        serializable?.let{
            exercise = serializable as Exercise
        }
        serializable  = super.getIntent().getSerializableExtra("editExercise")
        serializable?.let{
            exercise = serializable as Exercise
            edit=true
        }
        val title  = findViewById<TextView>(R.id.navBarTitle)
        title.text = exercise.getName()
        val seriesPicker = findViewById<LinearLayout>(R.id.seriesPicker)
        // Back to previous window button
        val backButton = findViewById<ImageView>(R.id.navBarAction)
        backButton.setOnClickListener{
         if(edit){
                startActivity(  Intent(applicationContext, CreateTrainingActivity::class.java))
                overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)

            } else{
                startActivity( Intent(applicationContext, EnterExerciseNameActivity::class.java))
                overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)
            }
        }
        //Setting series scrollView
        val seriesScroll = findViewById<HorizontalScrollView>(R.id.seriesScroll)

        //Setting repsPicker
        val repsPicker = findViewById<NumberPicker>(R.id.repsPicker)
        repsPicker.maxValue = 50
        repsPicker.minValue = 0
        repsPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            exercise.list[selectedSeries - 1].reps = newVal


        }


        //Setting weightPicker
        val weightPicker = findViewById<NumberPicker>(R.id.weightPicker)
        weightPicker.maxValue = 300
        weightPicker.minValue = 0
        weightPicker.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            exercise.list[selectedSeries - 1].weight = newVal
        })
        //Create first series
         val firstButton = createSeriesButton("1")
         addButtonToList(firstButton, seriesPicker, seriesScroll, weightPicker, repsPicker)
        val secondButton = createSeriesButton("+")
        addButtonToList(secondButton, seriesPicker, seriesScroll, weightPicker, repsPicker)
        if(edit)
        {
            for(i in 0 until exercise.list.size)
            {
                if(exercise.list[i].seriesNumber == 1)
                {
                    weightPicker.value = exercise.list[selectedSeries - 1].weight
                    repsPicker.value = exercise.list[selectedSeries - 1].reps
                }
                else
                {
                    seriesCounter++
                    val button2 = createSeriesButton(seriesCounter.toString())
                    addButtonToList(button2, seriesPicker, seriesScroll, weightPicker, repsPicker)
                    val b = seriesPicker[seriesCounter - 1]
                    seriesPicker.removeViewAt(seriesCounter - 1)
                    seriesPicker.addView(b)
                }
            }
        }
        seriesPicker[0].callOnClick()
        ready = true
        if(exercise.getPart()?.getName().equals("cardio"))
        {
            val pickerText1 = findViewById<TextView>(R.id.pickerText1)
            val pickerText2 = findViewById<TextView>(R.id.pickerText2)
            pickerText1.text = resources.getString(R.string.minutes)
            pickerText2.text = resources.getString(R.string.meters)
            weightPicker.minValue = 0
            weightPicker.maxValue = (createMetersArray().size-1)
            weightPicker.displayedValues = createMetersArray()

        }

        val endButton = findViewById<Button>(R.id.endWindow)
        endButton.setOnClickListener{

            val intent = Intent(applicationContext, CreateTrainingActivity::class.java)
            if(!edit)
                intent.putExtra("Exercise", exercise)
            else
                intent.putExtra("editedExercise", exercise)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in_animation,R.anim.fade_out_animation)
        }
        val deleteSeries = findViewById<Button>(R.id.deleteSeries)
        deleteSeries.setOnClickListener{

            if(seriesCounter > 1)
            {
                seriesCounter--
                exercise.list.removeAt(seriesCounter)
                seriesPicker.removeViewAt(seriesCounter)
                selectedSeries = seriesCounter
                val b = seriesPicker[seriesCounter - 1] as Button
                weightPicker.value = exercise.list[Integer.valueOf(b.text as String) - 1].weight
                repsPicker.value = exercise.list[Integer.valueOf(b.text as String) - 1].reps
                b.setBackgroundDrawable(resources.getDrawable(R.color.newBlue))
            }
        }
    }
    private fun createSeriesButton(text: String) : Button
    {
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        params.setMargins(10, 10, 10, 10)
        val button = Button(this)
        button.text = text

        button.layoutParams = params

        return button
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addButtonToList(
        button: Button,
        list: LinearLayout,
        seriesScroll: HorizontalScrollView,
        weightPicker: NumberPicker,
        repsPicker: NumberPicker
    )
    {
        val animation: Animation = AnimationUtils.loadAnimation(this,R.anim.slide_in_left_animation)

        //Make all button gray
        for(i in 0..list.childCount -2)
        {
            val temp = list.getChildAt(i) as Button
            temp.setBackgroundDrawable(resources.getDrawable(R.color.newBlack))
        }
        //setting onCLick listener
        if(button.text == "+")
        {
             button.setOnClickListener{
                 seriesCounter++
                 val button2 = createSeriesButton(seriesCounter.toString())
                 addButtonToList(button2, list, seriesScroll, weightPicker, repsPicker)
                 val b = list[seriesCounter - 1]
                 list.removeViewAt(seriesCounter - 1)
                 list.addView(b)

                 b.startAnimation(animation)

                 seriesScroll.post(Runnable { seriesScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT) })
             }
            button.setBackgroundDrawable(resources.getDrawable(R.color.newGreen))

            list.addView(button)

        }
        else
        {

            button.setOnClickListener{
                for(i in 0..list.childCount -2)
                {
                    val temp = list.getChildAt(i) as Button
                    if(button.text == temp.text)
                        continue
                    else
                        temp.setBackgroundDrawable(resources.getDrawable(R.color.newBlack))

                }
                button.setBackgroundDrawable(resources.getDrawable(R.color.newBlue))

                selectedSeries = Integer.valueOf(button.text as String)
                weightPicker.value = exercise.list[Integer.valueOf(button.text as String) - 1].weight
                repsPicker.value = exercise.list[Integer.valueOf(button.text as String) - 1].reps
            }
            button.setBackgroundDrawable(resources.getDrawable(R.color.newBlue))
            selectedSeries = Integer.valueOf(button.text as String)
            val buttonText = button.text as String
            if(!edit || ready)
            {
                val series = Series(Integer.valueOf(buttonText), 0, 0)
                exercise.addSeries(series)
            }

            list.addView(button)


            if(seriesCounter > 1)
            {
                button.startAnimation(animation)
                if(!edit || ready)
                {
                    exercise.list[Integer.valueOf(button.text as String) - 1].weight = exercise.list[Integer.valueOf(
                        button.text as String
                    ) - 2].weight
                    exercise.list[Integer.valueOf(button.text as String) - 1].reps = exercise.list[Integer.valueOf(
                        button.text as String
                    ) - 2].reps

                }

            }
        }
    }
    private fun createMetersArray():Array<out String>
    {
        return Array(100){ i -> (i *100).toString()};
    }

    override fun onBackPressed() {
        if(edit){
            startActivity(  Intent(applicationContext, CreateTrainingActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)
        } else{
            startActivity( Intent(applicationContext, EnterExerciseNameActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)
        }

    }
}