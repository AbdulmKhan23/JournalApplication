package com.khan.journalapplication.data.remote


data class ReflectQuestionRequest(
    val excluded_questions: List<String>
)

data class ReflectQuestionResponse(
    val question: String
)