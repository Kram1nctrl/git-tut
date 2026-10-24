package com.kram1nctrl.gittut.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kram1nctrl.gittut.data.Block
import com.kram1nctrl.gittut.data.CalloutKind
import com.kram1nctrl.gittut.ui.theme.CodeBackground
import com.kram1nctrl.gittut.ui.theme.CodeMuted
import com.kram1nctrl.gittut.ui.theme.CodeText

/** Clipboard helper that also shows a short confirmation toast. */
@Composable
fun rememberCopyAction(): (String) -> Unit {
    val clipboard = LocalClipboardManager.current
    val context = LocalContext.current
    return remember(clipboard, context) {
        { text ->
            clipboard.setText(AnnotatedString(text))
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * Renders the tiny inline Markdown used in the curriculum: `code` and **bold**.
 */
@Composable
fun inlineMarkdown(text: String): AnnotatedString {
    val codeBackground = MaterialTheme.colorScheme.surfaceVariant
    val codeColor = MaterialTheme.colorScheme.primary
    return remember(text, codeBackground, codeColor) {
        buildAnnotatedString {
            var i = 0
            while (i < text.length) {
                if (text.startsWith("**", i)) {
                    val end = text.indexOf("**", i + 2)
                    if (end > i + 2) {
                        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(text.substring(i + 2, end))
                        }
                        i = end + 2
                        continue
                    }
                }
                if (text[i] == '`') {
                    val end = text.indexOf('`', i + 1)
                    if (end > i) {
                        withStyle(
                            SpanStyle(
                                fontFamily = FontFamily.Monospace,
                                background = codeBackground,
                                color = codeColor,
                                fontSize = 14.sp,
                            ),
                        ) {
                            append(text.substring(i + 1, end))
                        }
                        i = end + 1
                        continue
                    }
                }
                append(text[i])
                i++
            }
        }
    }
}

@Composable
fun MarkdownBlock(block: Block, modifier: Modifier = Modifier) {
    when (block) {
        is Block.Heading -> {
            val style = when (block.level) {
                1 -> MaterialTheme.typography.headlineSmall
                2 -> MaterialTheme.typography.titleLarge
                else -> MaterialTheme.typography.titleMedium
            }
            Text(
                text = inlineMarkdown(block.text),
                style = style,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier,
            )
        }

        is Block.Paragraph -> Text(
            text = inlineMarkdown(block.text),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier,
        )

        is Block.Bullets -> Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            block.items.forEachIndexed { index, item ->
                Row {
                    Text(
                        text = if (block.ordered) "${index + 1}." else "•",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = inlineMarkdown(item),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        is Block.Code -> CodeSample(block.code, block.language, modifier)

        is Block.Callout -> CalloutCard(block, modifier)
    }
}

@Composable
fun CodeSample(code: String, language: String?, modifier: Modifier = Modifier) {
    val copy = rememberCopyAction()
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = CodeBackground,
        contentColor = CodeText,
        shape = RoundedCornerShape(14.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 6.dp, top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = (language ?: "terminal").lowercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontFamily = FontFamily.Monospace,
                    color = CodeMuted,
                )
                Spacer(Modifier.weight(1f))
                TextButton(onClick = { copy(code) }) {
                    Text("Copy", color = Color(0xFFFFB59B), style = MaterialTheme.typography.labelMedium)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 14.dp, end = 14.dp, bottom = 14.dp),
            ) {
                Text(
                    text = code,
                    color = CodeText,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                )
            }
        }
    }
}

@Composable
private fun CalloutCard(callout: Block.Callout, modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val (container, accent, content, label) = when (callout.kind) {
        CalloutKind.TIP -> CalloutStyle(scheme.tertiaryContainer, scheme.tertiary, scheme.onTertiaryContainer, "Tip")
        CalloutKind.WARN -> CalloutStyle(scheme.errorContainer, scheme.error, scheme.onErrorContainer, "Watch out")
        CalloutKind.NOTE -> CalloutStyle(scheme.surfaceVariant, scheme.primary, scheme.onSurfaceVariant, "Note")
    }
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = container,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, accent.copy(alpha = 0.35f)),
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = accent,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = inlineMarkdown(callout.text),
                style = MaterialTheme.typography.bodyMedium,
                color = content,
            )
        }
    }
}

private data class CalloutStyle(
    val container: Color,
    val accent: Color,
    val content: Color,
    val label: String,
)
