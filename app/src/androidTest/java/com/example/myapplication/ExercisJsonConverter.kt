package com.example.myapplication

import com.example.myapplication.exercises.Exercise

class UserAnswerJsonConverter(jsonFilename: String?) :
    JsonConverter<Exercise?>(jsonFilename!!)