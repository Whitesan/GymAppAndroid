package com.example.myapplication.activities.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.boyzdroizy.simpleandroidbarchart.SimpleBarChart
import com.example.myapplication.Constants.Companion.SAVED_TRAININGS_FILE
import com.example.myapplication.R
import com.example.myapplication.TrainingJsonConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment (private val filesDir: String) : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    fun checkDateLimit(date: LocalDate): Boolean {
        val today = LocalDate.now()
        val days: Long = ChronoUnit.DAYS.between(date, today)
        return days <= 7
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_statistics_tab, container, false)
        //val textView: TextView = root.findViewById(R.id.section_label)
        val simpleBarChart: SimpleBarChart = root.findViewById(R.id.simpleBarChart)

        pageViewModel.text.observe(viewLifecycleOwner) {

            val allSavedTrainings = TrainingJsonConverter.loadTrainingJson("$filesDir/$SAVED_TRAININGS_FILE")

            val intervalData = (7 downTo 1).map { it }.toMutableList()
            val chartData = (7 downTo 1).map { 0 }.toMutableList()

            if("$it" == "1") {
                for (t in allSavedTrainings.trainingList) {
                    val date = LocalDate.of(t.year, t.month, t.day)
                    if (checkDateLimit(date))
                        for (s in t.series)
                            s.restTime?.let {
                                chartData[date.dayOfWeek.value - 1] += it*10
                            }
                }
            }

            if("$it" == "2") {
                for (t in allSavedTrainings.trainingList){
                    val date = LocalDate.of(t.year, t.month, t.day)
                    if(checkDateLimit(date))
                        for(s in t.series)
                            s.doneWeight?.let {
                                chartData[date.dayOfWeek.value - 1] += it
                            }
                }
            }

            if("$it" == "3") {
                for (t in allSavedTrainings.trainingList){
                    val date = LocalDate.of(t.year, t.month, t.day)
                    if(checkDateLimit(date))
                        for(s in t.series)
                            s.doneReps?.let {
                                chartData[date.dayOfWeek.value - 1] += it
                            }
                }
            }

            simpleBarChart.setChartData(chartData, intervalData)
            simpleBarChart.setMaxValue(chartData.max()!!)
            simpleBarChart.setMinValue(0)
        }


        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, filesDir: String): PlaceholderFragment {
            return PlaceholderFragment(filesDir).apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}