package com.example.myapplication.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window

import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.cardview.widget.CardView
import com.example.myapplication.Constants
import com.example.myapplication.R

@Suppress("DEPRECATION")
class MainActivity : AppWindowActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setActivityTheme() /*Exclusive for MainActivity.kt*/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val settings = findViewById<ImageView>(R.id.navBarAction)
        settings.setOnClickListener {
            settingsWindowDialog()
        }

        val planer = findViewById<CardView>(R.id.cardViewPlanner)
        planer.setOnClickListener {
            startActivity(Intent(applicationContext, PlannerActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)
        }
        val statistics = findViewById<CardView>(R.id.cardViewStatistics)
        statistics.setOnClickListener {
            startActivity(Intent(applicationContext, StatisticsTabActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)

        }

        val training = findViewById<CardView>(R.id.cardViewTraining)
        training.setOnClickListener {
            startActivity(Intent(applicationContext, TrainingActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_left_animation)

        }

        val calendar = findViewById<CardView>(R.id.cardViewCalendar)
        calendar.setOnClickListener {
            startActivity(Intent(applicationContext, CalendarActivity::class.java))
            overridePendingTransition(R.anim.fade_in_animation,R.anim.slide_out_right_animation)
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
    private fun getThemeIdByTheme(themeId: Int): Int {
        when (themeId) {
            Constants.THEME_LIGHT -> return R.id.light
            Constants.THEME_DARK -> return R.id.dark
        }
        return R.id.light
    }

    private fun prepareView(view: View) {
        //en/pl lang
        val selectedLang = view.findViewById<RadioButton>(getLangIdByStr(Constants.LANG_CURRENT))
        selectedLang.isChecked = true

        //night/light mode
        val selectedTheme = view.findViewById<RadioButton>(getThemeIdByTheme(Constants.THEME_CURRENT))
        selectedTheme.isChecked = true
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
            //Language
            var radioGroup = view.findViewById<RadioGroup>(R.id.languageRadios)
            var id = radioGroup.checkedRadioButtonId
            var radioButton = view.findViewById<RadioButton>(id)
            var tag = radioButton.tag.toString()
            setLangPref(tag)

            //Theme
            radioGroup = view.findViewById<RadioGroup>(R.id.themeRadios)
            id = radioGroup.checkedRadioButtonId
            radioButton = view.findViewById<RadioButton>(id)
            tag = radioButton.tag.toString()
            when(tag) {
                "dark" -> if(setThemePref(Constants.THEME_DARK)) restartActivity()
                "light" -> if(setThemePref(Constants.THEME_LIGHT)) restartActivity()
            }
        }

        builder.setNegativeButton(cancel) { _, _ ->
            return@setNegativeButton
        }
        builder.show()
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}