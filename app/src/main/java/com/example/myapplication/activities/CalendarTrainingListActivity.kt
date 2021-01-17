package com.example.myapplication.activities



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Constants
import com.example.myapplication.R
import com.example.myapplication.TrainingJsonConverter
import com.example.myapplication.exercises.CurrentDay
import com.example.myapplication.exercises.Training
import com.example.myapplication.exercises.Trainings
import com.example.myapplication.recycler_view.CalendarTrainingListAdapter
import com.example.myapplication.recycler_view.TrainingListAdapter

@Suppress("DEPRECATION")
class CalendarTrainingListActivity : AppWindowActivity() {
    private var day: Int = -1
    private lateinit var trainingsList : Trainings

    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme()
        super.onCreate(savedInstanceState)

        day = intent.extras?.get("day") as Int
        Log.d("Tag", day.toString())

        setContentView(R.layout.activity_calendar_trainings_list)
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setOnClickListener{onBackPressed()}

        val buttonDel = findViewById<ImageView>(R.id.navBarRemove)
        buttonDel.setOnClickListener{onDeletePressed()}

        //Loading list from JSON file
        //List is using by recycle_viewer
        trainingsList = TrainingJsonConverter.loadTrainingJson("$filesDir/${Constants.TRAINING_FILE}")

        createVisualTrainingsList()
    }

    private fun createVisualTrainingsList() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_training_list)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = CalendarTrainingListAdapter(trainingsList.trainingList, "$filesDir/${Constants.TRAINING_FILE}", this, day)
    }

    /*Remove current day from trainings just by iterate over all of them*/
    private fun onDeletePressed(){
        val tl : ArrayList<Training> = trainingsList.trainingList

        for(t : Training in tl){
            if(t.hasDay(day))
                t.getDays().remove(day);
        }

        val trainings = Trainings(tl)
        val json: TrainingJsonConverter = TrainingJsonConverter()
        json.toJson(trainings, "$filesDir/${Constants.TRAINING_FILE}")

        //Remove and back to calendar
        onBackPressed()
    }
    override fun onBackPressed() {
        val intent =Intent(applicationContext, CalendarActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in_animation, R.anim.slide_out_left_animation)
    }
}