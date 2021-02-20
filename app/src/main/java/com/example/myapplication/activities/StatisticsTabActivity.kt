package com.example.myapplication.activities

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.example.myapplication.R
import com.example.myapplication.activities.ui.main.SectionsPagerAdapter

class StatisticsTabActivity : AppWindowActivity() {
    //TODO Charts from json data
    //json data -> SAVED_TRAININGS_FILE is a list of all saved trainings (done workouts)
    //training has a date of workout  :LocalDateTime?
    //each series contains:
    // done weight :Int?
    // done reps   :Int?
    // rest time   :Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityTheme()
        setContentView(R.layout.activity_statistics_tab)
        val button = findViewById<ImageView>(R.id.navBarAction)
        button.setBackListener(R.anim.fade_in_animation,R.anim.slide_out_left_animation)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)
    }
}