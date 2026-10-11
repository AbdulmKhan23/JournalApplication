package com.khan.journalapplication.data.remote


import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class ReflectQuestionStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val preferences = context.getSharedPreferences(
        "reflection_question_history",
        Context.MODE_PRIVATE
    )

    fun getPreviousQuestions(): Set<String> {
        return preferences
            .getStringSet(QUESTIONS_KEY, emptySet())
            ?.toSet()
            .orEmpty()
    }

    fun rememberQuestion(question: String) {
        val updatedQuestions = getPreviousQuestions()
            .toMutableSet()
            .apply { add(question.trim()) }

        preferences.edit {
            putStringSet(QUESTIONS_KEY, updatedQuestions)
        }
    }

    companion object {
        private const val QUESTIONS_KEY = "generated_questions"
    }
}