package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class PlannerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner)
        var button = findViewById(R.id.button) as Button
        button.setOnClickListener(object: View.OnClickListener
        {
            override fun onClick(v: View?) {
                val intent =  Intent(applicationContext,MainActivity::class.java)
                startActivity(intent)
            }
        })

    }
}