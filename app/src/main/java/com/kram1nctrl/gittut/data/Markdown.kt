package com.kram1nctrl.gittut.data

/**
 * A deliberately small Markdown subset, just enough for the lesson content:
 * headings, paragraphs, fenced code, blockquote callouts, and lists.
 *
 * Callouts use GitHub-style markers:
 *   > [!TIP] text
 *   > [!WARNING] text
 *   > [!NOTE] text
 */
object Markdown {

    fun parse(source: String): List<Block> {
        val blocks = mutableListOf<Block>()
        val lines = source.replace("\r\n", "\n").split("\n")
        val paragraph = mutableListOf<String>()

        fun flushParagraph() {
            if (paragraph.isNotEmpty()) {
                blocks += Block.Paragraph(paragraph.joinToString(" ").trim())
                paragraph.clear()
            }
        }

        var i = 0
        while (i < lines.size) {
            val line = lines[i].trimEnd()

            when {
                line.startsWith("```") -> {
                    flushParagraph()
                    val language = line.removePrefix("```").trim().ifEmpty { null }
                    val code = mutableListOf<String>()
                    i++
                    while (i < lines.size && !lines[i].trimStart().startsWith("```")) {
                        code += lines[i]
                        i++
                    }
                    blocks += Block.Code(code.joinToString("\n").trim('\n'), language)
                }

                line.startsWith("### ") -> {
                    flushParagraph()
                    blocks += Block.Heading(line.removePrefix("### ").trim(), 3)
                }

                line.startsWith("## ") -> {
                    flushParagraph()
                    blocks += Block.Heading(line.removePrefix("## ").trim(), 2)
                }

                line.startsWith("# ") -> {
                    flushParagraph()
                    blocks += Block.Heading(line.removePrefix("# ").trim(), 1)
                }

                line.trimStart().startsWith(">") -> {
                    flushParagraph()
                    val quoted = mutableListOf<String>()
                    while (i < lines.size && lines[i].trimStart().startsWith(">")) {
                        quoted += lines[i].trimStart()
                            .removePrefix(">")
                            .removePrefix(" ")
                            .trimEnd()
                        i++
                    }
                    var text = quoted.joinToString("\n").trim()
                    var kind = CalloutKind.NOTE
                    when {
                        text.startsWith("[!TIP]") -> {
                            kind = CalloutKind.TIP
                            text = text.removePrefix("[!TIP]").trim()
                        }
                        text.startsWith("[!WARNING]") -> {
                            kind = CalloutKind.WARN
                            text = text.removePrefix("[!WARNING]").trim()
                        }
                        text.startsWith("[!NOTE]") -> {
                            text = text.removePrefix("[!NOTE]").trim()
                        }
                    }
                    if (text.isNotEmpty()) blocks += Block.Callout(text, kind)
                    continue
                }

                line.trimStart().startsWith("- ") -> {
                    flushParagraph()
                    val items = mutableListOf<String>()
                    while (i < lines.size && lines[i].trimStart().startsWith("- ")) {
                        items += lines[i].trimStart().removePrefix("- ").trim()
                        i++
                    }
                    blocks += Block.Bullets(items, ordered = false)
                    continue
                }

                ORDERED_ITEM.matches(line.trimStart()) -> {
                    flushParagraph()
                    val items = mutableListOf<String>()
                    while (i < lines.size && ORDERED_ITEM.matches(lines[i].trimStart())) {
                        items += lines[i].trimStart().replaceFirst(ORDERED_PREFIX, "")
                        i++
                    }
                    blocks += Block.Bullets(items, ordered = true)
                    continue
                }

                line.isBlank() -> flushParagraph()

                else -> paragraph += line.trim()
            }
            i++
        }
        flushParagraph()
        return blocks
    }

    private val ORDERED_PREFIX = Regex("^\\d+\\.\\s+")
    private val ORDERED_ITEM = Regex("^\\d+\\.\\s+.*")
}
