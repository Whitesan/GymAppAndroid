package com.example.myapplication

import com.example.myapplication.exercises.Exercise

class QuestionJsonConverter(jsonFilename: String?) :
    JsonConverter<Exercise?>(jsonFilename!!)