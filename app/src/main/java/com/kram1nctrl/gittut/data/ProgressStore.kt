package com.kram1nctrl.gittut.data

import android.content.Context

/** Stores lesson completion and best quiz scores locally. */
class ProgressStore(context: Context) {

    private val prefs = context.getSharedPreferences("gittut_progress", Context.MODE_PRIVATE)

    fun completedLessons(): Set<String> =
        prefs.getStringSet(KEY_COMPLETED, emptySet())?.toSet() ?: emptySet()

    fun setLessonCompleted(lessonId: String, completed: Boolean) {
        val current = completedLessons().toMutableSet()
        if (completed) current += lessonId else current -= lessonId
        prefs.edit().putStringSet(KEY_COMPLETED, current).apply()
    }

    fun quizScores(): Map<String, QuizScore> {
        val scores = mutableMapOf<String, QuizScore>()
        for ((key, value) in prefs.all) {
            if (!key.startsWith(QUIZ_PREFIX)) continue
            val raw = value as? String ?: continue
            val parts = raw.split("/")
            if (parts.size != 2) continue
            val score = parts[0].toIntOrNull() ?: continue
            val total = parts[1].toIntOrNull() ?: continue
            if (total > 0) scores[key.removePrefix(QUIZ_PREFIX)] = QuizScore(score, total)
        }
        return scores
    }

    fun saveQuizScore(chapterId: String, score: Int, total: Int) {
        if (total <= 0) return
        val previous = quizScores()[chapterId]
        val isBetter = previous == null || score.toFloat() / total > previous.score.toFloat() / previous.total
        if (isBetter) {
            prefs.edit().putString("$QUIZ_PREFIX$chapterId", "$score/$total").apply()
        }
    }

    private companion object {
        const val KEY_COMPLETED = "completed_lessons"
        const val QUIZ_PREFIX = "quiz_"
    }
}
