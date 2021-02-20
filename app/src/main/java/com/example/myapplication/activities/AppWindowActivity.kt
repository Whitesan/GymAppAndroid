package com.example.myapplication.activities

import android.content.Context
import android.content.res.Configuration
import android.graphics.Paint
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Constants
import java.lang.reflect.Field
import java.util.*


open class AppWindowActivity : AppCompatActivity() {
    init {
        updateConfig(this)
    }

    private fun updateConfig(wrapper: ContextThemeWrapper) {
        val dLocale = Locale(Constants.LANG_CURRENT)
        Locale.setDefault(dLocale)
        val configuration = Configuration()
        configuration.setLocale(dLocale)
        wrapper.applyOverrideConfiguration(configuration)
    }

    final override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
     private fun hideSystemUI() {
         window.setFlags(
             WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN,
         )
    }
    final override fun onResume() {
        super.onResume()
        hideSystemUI()
    }
     fun View.hideKeyboard(){
        hideSystemUI()
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }

    /* ----------- Theme ----------- */
    protected fun setThemePref(theme: Int): Boolean{
        if(theme == Constants.THEME_CURRENT)
            return false
        val pref = this.getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE)
        with(pref.edit()) {
            putInt(Constants.THEME, theme)
            apply()
        }
        Constants.THEME_CURRENT = theme
        return  true
    }
    private fun getThemePref(): Int{
        val pref = this.getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE)
        return pref.getInt(Constants.THEME, Constants.THEME_LIGHT)
    }
    protected fun setActivityTheme(){
        val theme = getThemePref()
        if(Constants.THEME_CURRENT == -1)
            Constants.THEME_CURRENT = theme
        this.setTheme(theme)
    }
    protected fun View.setBackListener(enterAnim: Int?, exitAnim: Int?){
        setOnClickListener{
            onBackPressed();
            if(enterAnim!= null && exitAnim!=null)
                overridePendingTransition(enterAnim, exitAnim)
        }
    }


}