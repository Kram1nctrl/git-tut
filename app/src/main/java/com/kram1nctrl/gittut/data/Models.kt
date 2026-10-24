package com.kram1nctrl.gittut.data

/** A chapter groups lessons and a quiz. */
data class Chapter(
    val id: String,
    val number: Int,
    val title: String,
    val subtitle: String,
    val emoji: String,
    val lessons: List<LessonMeta>,
    val quiz: List<QuizQuestion>,
)

data class LessonMeta(
    val id: String,
    val chapterId: String,
    val title: String,
    val summary: String,
    val minutes: Int,
    val file: String,
)

data class LessonContent(
    val meta: LessonMeta,
    val blocks: List<Block>,
) {
    /** Plain text used by search, including headings and code samples. */
    val plainText: String by lazy {
        blocks.joinToString("\n") { it.plainText() }
    }

    /** Body text without headings, used to build readable search snippets. */
    val searchText: String by lazy {
        blocks.filterNot { it is Block.Heading }
            .joinToString("\n") { it.plainText() }
    }
}

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val answer: Int,
    val explanation: String,
)

sealed interface Block {
    data class Heading(val text: String, val level: Int) : Block
    data class Paragraph(val text: String) : Block
    data class Code(val code: String, val language: String?) : Block
    data class Callout(val text: String, val kind: CalloutKind) : Block
    data class Bullets(val items: List<String>, val ordered: Boolean) : Block
}

private fun Block.plainText(): String = when (this) {
    is Block.Heading -> text
    is Block.Paragraph -> text
    is Block.Code -> code
    is Block.Callout -> text
    is Block.Bullets -> items.joinToString(" ")
}

enum class CalloutKind { TIP, WARN, NOTE }

data class CheatGroup(val title: String, val items: List<CheatItem>)

data class CheatItem(val command: String, val description: String)

data class Content(
    val chapters: List<Chapter>,
    val lessons: Map<String, LessonContent>,
    val cheatSheet: List<CheatGroup>,
) {
    val totalLessons: Int get() = chapters.sumOf { it.lessons.size }

    fun chapter(id: String): Chapter? = chapters.firstOrNull { it.id == id }

    fun lesson(id: String): LessonContent? = lessons[id]

    fun allLessonMetas(): List<LessonMeta> = chapters.flatMap { it.lessons }
}

data class QuizScore(val score: Int, val total: Int)

data class SearchResult(
    val lesson: LessonMeta,
    val chapter: Chapter,
    val snippet: String,
)
