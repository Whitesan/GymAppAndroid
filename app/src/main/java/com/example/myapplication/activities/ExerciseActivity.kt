
package com.example.myapplication.activities
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.HorizontalScrollView
import android.widget.NumberPicker.OnValueChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.myapplication.R
import com.example.myapplication.exercises.Exercise
import com.example.myapplication.exercises.Part
import com.example.myapplication.exercises.Series


@Suppress("DEPRECATION")
class ExerciseActivity : AppWindowActivity() {

    lateinit var exercise : Exercise
    var seriesCounter = 1
    var selectedSeries = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        exercise = super.getIntent().getSerializableExtra("Exercise") as Exercise
        Toast.makeText(this, exercise.toString(), Toast.LENGTH_SHORT).show()
        val title  = findViewById<TextView>(R.id.plannerTitle)
        title.text = exercise.getName()
        val seriesPicker = findViewById<LinearLayout>(R.id.seriesPicker)
        // Back to previous window button
        val backButton = findViewById<ImageView>(R.id.backExercise)
        backButton.setOnClickListener{
            val intent =  Intent(applicationContext, EnterExerciseNameActivity::class.java)
            startActivity(intent)
        }

        //Setting series scrollView
        val seriesScroll = findViewById<HorizontalScrollView>(R.id.seriesScroll)

        //Setting repsPicker
        val repsPicker = findViewById<NumberPicker>(R.id.repsPicker)
        repsPicker.maxValue = 100
        repsPicker.minValue = 0
        repsPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            exercise.list[selectedSeries - 1].reps = newVal
        }


        //Setting weightPicker
        val weightPicker = findViewById<NumberPicker>(R.id.weightPicker)
        weightPicker.maxValue = 500
        weightPicker.minValue = 0
        weightPicker.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            exercise.list[selectedSeries - 1].weight = newVal
        })
        //Create first series

        val firstButton = createSeriesButton("1")
        addButtonToList(firstButton,seriesPicker,seriesScroll,weightPicker,repsPicker);
        val secondButton = createSeriesButton("+")
        addButtonToList(secondButton,seriesPicker,seriesScroll,weightPicker,repsPicker);

        
        val endButton = findViewById<Button>(R.id.endWindow)
        endButton.setOnClickListener{
            val intent = Intent(applicationContext, CreateTrainingActivity::class.java)
            intent.putExtra("Exercise", exercise)
            startActivity(intent)
        }
        val deleteSeries = findViewById<Button>(R.id.deleteSeries)
        deleteSeries.setOnClickListener{

            if(seriesCounter > 1)
            {
                seriesCounter--;
                exercise.list.removeAt(seriesCounter);
                seriesPicker.removeViewAt(seriesCounter);

                Log.i("xd",seriesCounter.toString() + " " + selectedSeries.toString())
                selectedSeries = seriesCounter
                val b = seriesPicker[seriesCounter-1] as Button
                Log.i("xd2",b.text.toString())
                weightPicker.value = exercise.list[Integer.valueOf(b.text as String) - 1].weight;
                repsPicker.value = exercise.list[Integer.valueOf(b.text as String) - 1].reps;
                b.setBackgroundDrawable(getResources().getDrawable(R.color.blue));
            }
        }
    }
    private fun createSeriesButton(text : String) : Button
    {
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        params.setMargins(10, 10, 10, 10)
        val button = Button(this)
        button.text = text

        button.layoutParams = params
        return button;
    }
    private fun addButtonToList(button: Button, list : LinearLayout, seriesScroll : HorizontalScrollView, weightPicker:NumberPicker, repsPicker:NumberPicker)
    {
        //Make all button gray
        for(i in 0..list.childCount -2)
        {
            val temp = list.getChildAt(i) as Button
            temp.setBackgroundDrawable(getResources().getDrawable(R.color.graphite));
        }
        //setting onCLick listener
        if(button.text == "+")
        {
             button.setOnClickListener{
                 seriesCounter++;
                 val button2 = createSeriesButton(seriesCounter.toString())
                 addButtonToList(button2,list,seriesScroll,weightPicker,repsPicker);
                 val b = list[seriesCounter - 1]
                 list.removeViewAt(seriesCounter - 1)
                 list.addView(b)

                 seriesScroll.post(Runnable { seriesScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT) })
             }
            button.setBackgroundDrawable(getResources().getDrawable(R.color.green));
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
                        temp.setBackgroundDrawable(getResources().getDrawable(R.color.graphite));

                }
                button.setBackgroundDrawable(getResources().getDrawable(R.color.blue))
                selectedSeries = Integer.valueOf(button.text as String)
                weightPicker.value = exercise.list[Integer.valueOf(button.text as String) - 1].weight;
                repsPicker.value = exercise.list[Integer.valueOf(button.text as String) - 1].reps;
            }
            button.setBackgroundDrawable(getResources().getDrawable(R.color.blue));
            selectedSeries = Integer.valueOf(button.text as String)
            val buttonText = button.text as String
            val series = Series(Integer.valueOf(buttonText),0,0)
            exercise.addSeries(series)
            list.addView(button)
            if(seriesCounter > 1)
            {
                exercise.list[Integer.valueOf(button.text as String) - 1].weight = exercise.list[Integer.valueOf(button.text as String) - 2].weight;
                exercise.list[Integer.valueOf(button.text as String) - 1].reps = exercise.list[Integer.valueOf(button.text as String) - 2].reps;

            }
        }
    }
}