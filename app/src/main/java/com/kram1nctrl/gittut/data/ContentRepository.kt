package com.kram1nctrl.gittut.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

/** Loads the bundled offline curriculum from assets/content. */
class ContentRepository(private val context: Context) {

    suspend fun load(): Content = withContext(Dispatchers.IO) {
        val root = JSONObject(readAsset("content/index.json"))
        val chaptersJson = root.getJSONArray("chapters")
        val chapters = mutableListOf<Chapter>()
        val lessons = mutableMapOf<String, LessonContent>()

        for (c in 0 until chaptersJson.length()) {
            val chapterJson = chaptersJson.getJSONObject(c)
            val chapterId = chapterJson.getString("id")

            val lessonMetas = mutableListOf<LessonMeta>()
            val lessonsJson = chapterJson.getJSONArray("lessons")
            for (l in 0 until lessonsJson.length()) {
                val lessonJson = lessonsJson.getJSONObject(l)
                val meta = LessonMeta(
                    id = lessonJson.getString("id"),
                    chapterId = chapterId,
                    title = lessonJson.getString("title"),
                    summary = lessonJson.optString("summary"),
                    minutes = lessonJson.optInt("minutes", 4),
                    file = lessonJson.getString("file"),
                )
                lessonMetas += meta
                lessons[meta.id] = LessonContent(
                    meta = meta,
                    blocks = Markdown.parse(readAsset("content/lessons/${meta.file}")),
                )
            }

            val quiz = mutableListOf<QuizQuestion>()
            chapterJson.optJSONArray("quiz")?.let { quizJson ->
                for (q in 0 until quizJson.length()) {
                    val question = quizJson.getJSONObject(q)
                    val options = question.getJSONArray("options")
                    quiz += QuizQuestion(
                        question = question.getString("q"),
                        options = List(options.length()) { options.getString(it) },
                        answer = question.getInt("answer"),
                        explanation = question.optString("explain"),
                    )
                }
            }

            chapters += Chapter(
                id = chapterId,
                number = chapterJson.optInt("number"),
                title = chapterJson.getString("title"),
                subtitle = chapterJson.optString("subtitle"),
                emoji = chapterJson.optString("emoji", "📘"),
                lessons = lessonMetas,
                quiz = quiz,
            )
        }

        Content(chapters, lessons, readCheatSheet())
    }

    private fun readCheatSheet(): List<CheatGroup> {
        val root = JSONObject(readAsset("content/cheatsheet.json"))
        val groups = root.getJSONArray("groups")
        return List(groups.length()) { g ->
            val group = groups.getJSONObject(g)
            val items = group.getJSONArray("items")
            CheatGroup(
                title = group.getString("title"),
                items = List(items.length()) { i ->
                    val item = items.getJSONObject(i)
                    CheatItem(item.getString("cmd"), item.getString("desc"))
                },
            )
        }
    }

    private fun readAsset(path: String): String =
        context.assets.open(path).bufferedReader().use { it.readText() }
}
