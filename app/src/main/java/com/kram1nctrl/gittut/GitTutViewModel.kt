package com.kram1nctrl.gittut

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kram1nctrl.gittut.data.Chapter
import com.kram1nctrl.gittut.data.Content
import com.kram1nctrl.gittut.data.ContentRepository
import com.kram1nctrl.gittut.data.LessonContent
import com.kram1nctrl.gittut.data.LessonMeta
import com.kram1nctrl.gittut.data.ProgressStore
import com.kram1nctrl.gittut.data.QuizScore
import com.kram1nctrl.gittut.data.SearchResult
import kotlinx.coroutines.launch

class GitTutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ContentRepository(application)
    private val store = ProgressStore(application)

    var content by mutableStateOf<Content?>(null)
        private set
    var loading by mutableStateOf(true)
        private set
    var error by mutableStateOf<String?>(null)
        private set
    var completed by mutableStateOf(store.completedLessons())
        private set
    var quizScores by mutableStateOf(store.quizScores())
        private set

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            loading = true
            error = null
            runCatching { repository.load() }
                .onSuccess { content = it }
                .onFailure { error = it.message ?: "Could not load the lessons." }
            loading = false
        }
    }

    fun isCompleted(lessonId: String): Boolean = lessonId in completed

    fun toggleCompleted(lessonId: String) {
        store.setLessonCompleted(lessonId, !isCompleted(lessonId))
        completed = store.completedLessons()
    }

    fun saveQuizResult(chapterId: String, score: Int, total: Int) {
        store.saveQuizScore(chapterId, score, total)
        quizScores = store.quizScores()
    }

    fun quizScore(chapterId: String): QuizScore? = quizScores[chapterId]

    fun chapterProgress(chapter: Chapter): Float {
        if (chapter.lessons.isEmpty()) return 0f
        return chapter.lessons.count { it.id in completed }.toFloat() / chapter.lessons.size
    }

    fun overallProgress(): Float {
        val total = content?.totalLessons ?: return 0f
        if (total == 0) return 0f
        val done = content?.allLessonMetas()?.count { it.id in completed } ?: 0
        return done.toFloat() / total
    }

    fun nextLesson(): LessonContent? {
        val current = content ?: return null
        val meta = current.allLessonMetas().firstOrNull { it.id !in completed } ?: return null
        return current.lesson(meta.id)
    }

    fun lessonMeta(lessonId: String): LessonMeta? = content?.lesson(lessonId)?.meta

    fun nextLessonInChapter(chapterId: String, lessonId: String): LessonMeta? {
        val chapter = content?.chapter(chapterId) ?: return null
        val index = chapter.lessons.indexOfFirst { it.id == lessonId }
        if (index < 0) return null
        return chapter.lessons.getOrNull(index + 1)
    }

    fun search(query: String): List<SearchResult> {
        val current = content ?: return emptyList()
        val trimmed = query.trim()
        if (trimmed.length < 2) return emptyList()
        return current.lessons.values
            .filter { lesson ->
                lesson.meta.title.contains(trimmed, true) ||
                    lesson.meta.summary.contains(trimmed, true) ||
                    lesson.plainText.contains(trimmed, true)
            }
            .mapNotNull { lesson ->
                val chapter = current.chapter(lesson.meta.chapterId) ?: return@mapNotNull null
                SearchResult(lesson.meta, chapter, snippet(lesson, trimmed))
            }
            .sortedBy { it.lesson.title.lowercase().indexOf(trimmed.lowercase()).let { i -> if (i < 0) 100 else i } }
            .take(30)
    }

    private fun snippet(lesson: LessonContent, query: String): String {
        val text = lesson.plainText
        val index = text.indexOf(query, ignoreCase = true)
        if (index < 0) return lesson.meta.summary.ifBlank { text.take(120) }
        val start = (index - 45).coerceAtLeast(0)
        val end = (index + query.length + 75).coerceAtMost(text.length)
        val prefix = if (start > 0) "…" else ""
        val suffix = if (end < text.length) "…" else ""
        return prefix + text.substring(start, end).replace(Regex("\\s+"), " ").trim() + suffix
    }
}
