package com.example.myapplication.activities

import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


open class AppWindowActivity : AppCompatActivity() {
    final override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
    final protected fun hideSystemUI() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
    final override fun onResume() {
        super.onResume()
        hideSystemUI()
    }
}