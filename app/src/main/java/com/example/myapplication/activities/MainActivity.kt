package com.example.myapplication.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View

import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.cardview.widget.CardView
import com.example.myapplication.Constants
import com.example.myapplication.R

@Suppress("DEPRECATION")
class MainActivity : AppWindowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val settings = findViewById<ImageView>(R.id.settings)
        settings.setOnClickListener {
            settingsWindowDialog()
        }

        val planer = findViewById<CardView>(R.id.cardViewPlanner)
        planer.setOnClickListener {
            startActivity(Intent(applicationContext, PlannerActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)
        }
        val statistics = findViewById<CardView>(R.id.cardViewStatistics)
        statistics.setOnClickListener {
            startActivity(Intent(applicationContext, StatisticsActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)

        }

        val training = findViewById<CardView>(R.id.cardViewTraining)
        training.setOnClickListener {
            startActivity(Intent(applicationContext, TrainingActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)

        }

        val calendar = findViewById<CardView>(R.id.cardViewCalendar)
        calendar.setOnClickListener {
            startActivity(Intent(applicationContext, CalendarActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)


        }
        getLangPref()
    }

    private fun restartActivity() {
        val intent = intent
        finish()
        startActivity(intent)
    }

    private fun setLangPref(localeCode: String) {
        if (Constants.LANG_CURRENT == localeCode)
            return

        val pref = this.getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE)
        with(pref.edit()) {
            putString(Constants.LANG, localeCode)
            apply()
        }
        Constants.LANG_OLD = Constants.LANG_CURRENT
        Constants.LANG_CURRENT = localeCode
        restartActivity()
    }

    private fun getLangPref() {
        val pref = this.getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE)
        Constants.LANG_OLD = Constants.LANG_CURRENT
        Constants.LANG_CURRENT = pref.getString(Constants.LANG, Constants.LANG_EN).toString()
        if (Constants.LANG_CURRENT != Constants.LANG_OLD)
            restartActivity()
    }

    private fun getLangIdByStr(localeCode: String): Int {
        when (localeCode) {
            "en" -> return R.id.en
            "pl" -> return R.id.pl
        }
        return R.id.en
    }

    private fun prepareView(view: View) {
        val selectedLang = view.findViewById<RadioButton>(getLangIdByStr(Constants.LANG_CURRENT))
        selectedLang.isChecked = true

        //night/light mode
    }

    private fun settingsWindowDialog() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialog)
        val view = LayoutInflater.from(this).inflate(R.layout.settings_dialog, null)
        prepareView(view)

        val title = getString(R.string.MA_name) //resources.getString(R.string.TLA_Title_Dialog)
        val save = getString(R.string.MA_save)//resources.getString(R.string.TLA_YES)
        val cancel = getString(R.string.MA_cancel)//resources.getString(R.string.TLA_NO)

        builder.setTitle(title)
        builder.setView(view)
        builder.setPositiveButton(save) { _, _ ->
            val radioGroup = view.findViewById<RadioGroup>(R.id.languageRadios)
            val id = radioGroup.checkedRadioButtonId
            val radioButton = view.findViewById<RadioButton>(id)
            setLangPref(radioButton.tag.toString())
            Log.i("settings", "saved")
        }

        builder.setNegativeButton(cancel) { _, _ ->
            Log.i("settings", "cancelled")
            return@setNegativeButton
        }
        builder.show()
    }

}