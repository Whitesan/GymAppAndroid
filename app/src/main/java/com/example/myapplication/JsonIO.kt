package com.example.myapplication

import org.json.JSONObject
import java.io.IOException
import java.text.ParseException

/**
 * Class with static function for JSON input/output operation
 *
 * @author Adrian Kucharski
 */
public final class JsonIO {
    companion object{
        /**
         * A static method for saving "Traning Plan" to JSON file
         *
         * @param     trening     TraningPlan that will be saved as a JSON file
         * @exception IOException If the plan couldn't be saved to a file
         */
        fun saveTraningPlan(/*Class that store the created training plan*/) {

        }

        /**
         * A static method for loading "Traning Plan" from a JSON file
         * that was saved by saveTrainingPlan method
         * @see saveTraningPlan
         *
         *  @param filepath  A path to a file that store a data saved by saveTrainingPlan method
         *  @exception IOException If the file couldn't be readed
         *  @exception ParseException If cannot create a TraningPlan from a JSON file
         */
        fun loadTraningPlan(/*a filename*/) /* : TraingPlan */ {
            if (false)
                throw IOException("Cannot read file")
            if (false)
                throw ParseException("Cannot create a object from a JSON file", 0)

            /*return TrainingPlan*/
        }
    }
}