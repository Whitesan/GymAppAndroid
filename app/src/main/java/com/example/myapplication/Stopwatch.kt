package com.example.myapplication

import android.widget.TextView
import java.util.*
import kotlin.concurrent.timerTask

class Stopwatch(startTimeSec: Long?, private val maxTimeSec: Long? ) {
    private var millis: Long = 0
    private var sec: Long = 0
    private var min: Long = 0

    init {
        if (startTimeSec != null) {
            addSec(startTimeSec)
        }
    }

    private fun addSec(time: Long) {
        if ((this.sec + time) / 60 >= 1) {
            addMin((this.sec + time) / 60)
        }
        this.sec = (this.sec + time) % 60
    }

    private fun addMin(time: Long) {
        this.min += this.min + time
    }

    private fun addMillis(time: Long) {
        if (maxTimeSec != null && min>= (maxTimeSec/60))
            return
        if ((this.millis + time) / 1000 >= 1) {
            addSec((this.millis + time) / 1000)
        }
        this.millis = (this.millis + time) % 1000
    }

    fun start(intervalMillis: Long?, view: TextView?) {
        val delay: Long = intervalMillis ?: 1000
        val timer = Timer()
        timer.schedule(timerTask {
            addMillis(delay)
            view?.text = getTime()
        }, delay, delay)
    }

    fun getTime(): String {
        return String.format(" %02d : %02d : %02d", min, sec, millis / 10)
    }

}