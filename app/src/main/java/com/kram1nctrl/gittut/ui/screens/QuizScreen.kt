package com.kram1nctrl.gittut.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kram1nctrl.gittut.GitTutViewModel
import com.kram1nctrl.gittut.data.Chapter
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: GitTutViewModel,
    chapter: Chapter,
    onBack: () -> Unit,
) {
    var index by rememberSaveable { mutableIntStateOf(0) }
    var selected by rememberSaveable { mutableIntStateOf(-1) }
    var score by rememberSaveable { mutableIntStateOf(0) }
    var finished by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Quiz", maxLines = 1)
                        Text(
                            chapter.title,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter,
        ) {
            if (chapter.quiz.isEmpty()) {
                Text("This chapter has no quiz yet.", modifier = Modifier.padding(24.dp))
                return@Box
            }

            if (finished) {
                ResultContent(
                    chapter = chapter,
                    score = score,
                    total = chapter.quiz.size,
                    best = viewModel.quizScore(chapter.id),
                    onRetry = {
                        index = 0
                        selected = -1
                        score = 0
                        finished = false
                    },
                    onBack = onBack,
                )
            } else {
                val question = chapter.quiz[index]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 760.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp)
                        .padding(top = 8.dp, bottom = 28.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = "Question ${index + 1} of ${chapter.quiz.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    LinearProgressIndicator(
                        progress = { (index + 1f) / chapter.quiz.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        strokeCap = StrokeCap.Round,
                        gapSize = 0.dp,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = question.question,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(Modifier.height(2.dp))

                    question.options.forEachIndexed { optionIndex, option ->
                        OptionRow(
                            letter = ('A' + optionIndex).toString(),
                            text = option,
                            state = when {
                                selected < 0 -> OptionState.IDLE
                                optionIndex == question.answer -> OptionState.CORRECT
                                optionIndex == selected -> OptionState.WRONG
                                else -> OptionState.DIM
                            },
                            onClick = {
                                if (selected < 0) {
                                    selected = optionIndex
                                    if (optionIndex == question.answer) score++
                                }
                            },
                        )
                    }

                    if (selected >= 0) {
                        val correct = selected == question.answer
                        Surface(
                            color = if (correct) MaterialTheme.colorScheme.tertiaryContainer
                            else MaterialTheme.colorScheme.errorContainer,
                            shape = RoundedCornerShape(14.dp),
                        ) {
                            Column(Modifier.padding(14.dp)) {
                                Text(
                                    text = if (correct) "Correct!" else "Not quite",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (correct) MaterialTheme.colorScheme.onTertiaryContainer
                                    else MaterialTheme.colorScheme.onErrorContainer,
                                )
                                if (!correct) {
                                    Text(
                                        text = "Correct answer: ${question.options[question.answer]}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onErrorContainer,
                                    )
                                }
                                if (question.explanation.isNotBlank()) {
                                    Spacer(Modifier.height(6.dp))
                                    Text(
                                        text = question.explanation,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (correct) MaterialTheme.colorScheme.onTertiaryContainer
                                        else MaterialTheme.colorScheme.onErrorContainer,
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = {
                                if (index == chapter.quiz.lastIndex) {
                                    viewModel.saveQuizResult(chapter.id, score, chapter.quiz.size)
                                    finished = true
                                } else {
                                    index++
                                    selected = -1
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(if (index == chapter.quiz.lastIndex) "See results" else "Next question")
                        }
                    }
                }
            }
        }
    }
}

private enum class OptionState { IDLE, CORRECT, WRONG, DIM }

@Composable
private fun OptionRow(
    letter: String,
    text: String,
    state: OptionState,
    onClick: () -> Unit,
) {
    val scheme = MaterialTheme.colorScheme
    val container = when (state) {
        OptionState.CORRECT -> scheme.tertiaryContainer
        OptionState.WRONG -> scheme.errorContainer
        else -> scheme.surface
    }
    val border = when (state) {
        OptionState.CORRECT -> scheme.tertiary
        OptionState.WRONG -> scheme.error
        else -> scheme.outlineVariant
    }
    val content = when (state) {
        OptionState.CORRECT -> scheme.onTertiaryContainer
        OptionState.WRONG -> scheme.onErrorContainer
        OptionState.DIM -> scheme.onSurfaceVariant
        OptionState.IDLE -> scheme.onSurface
    }

    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = container,
        border = BorderStroke(1.dp, border),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    letter,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = content,
                )
            }
            Spacer(Modifier.width(10.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = content,
                modifier = Modifier.weight(1f),
            )
            when (state) {
                OptionState.CORRECT -> Icon(
                    Icons.Filled.Check,
                    contentDescription = null,
                    tint = scheme.tertiary,
                    modifier = Modifier.size(20.dp),
                )
                OptionState.WRONG -> Icon(
                    Icons.Filled.Close,
                    contentDescription = null,
                    tint = scheme.error,
                    modifier = Modifier.size(20.dp),
                )
                else -> Unit
            }
        }
    }
}

@Composable
private fun ResultContent(
    chapter: Chapter,
    score: Int,
    total: Int,
    best: com.kram1nctrl.gittut.data.QuizScore?,
    onRetry: () -> Unit,
    onBack: () -> Unit,
) {
    val fraction = if (total == 0) 0f else score.toFloat() / total
    val (emoji, message) = when {
        fraction >= 0.8f -> "🎉" to "Excellent! You know your way around Git."
        fraction >= 0.5f -> "👍" to "Nice work. Review the explanations and try again for a perfect score."
        else -> "📚" to "Keep going! Re-read the lessons in this chapter and give it another shot."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 760.dp)
            .verticalScroll(rememberScrollState())
            .padding(PaddingValues(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 32.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(emoji, fontSize = 52.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            text = "$score / $total",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "${(fraction * 100).roundToInt()}% correct",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(8.dp))
        best?.let {
            Text(
                text = "Your best: ${it.score}/${it.total}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
        Spacer(Modifier.height(24.dp))
        Button(onClick = onRetry, modifier = Modifier.fillMaxWidth()) {
            Text("Try again")
        }
        Spacer(Modifier.height(8.dp))
        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back to chapter")
        }
    }
}
