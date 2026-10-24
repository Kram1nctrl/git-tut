package com.kram1nctrl.gittut.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kram1nctrl.gittut.GitTutViewModel
import com.kram1nctrl.gittut.data.Chapter
import com.kram1nctrl.gittut.ui.screens.ChapterScreen
import com.kram1nctrl.gittut.ui.screens.CheatSheetScreen
import com.kram1nctrl.gittut.ui.screens.LearnScreen
import com.kram1nctrl.gittut.ui.screens.LessonScreen
import com.kram1nctrl.gittut.ui.screens.QuizScreen
import com.kram1nctrl.gittut.ui.screens.SearchScreen

private data class Tab(val route: String, val label: String, val icon: ImageVector)

private val tabs = listOf(
    Tab("learn", "Learn", Icons.Filled.Home),
    Tab("search", "Search", Icons.Filled.Search),
    Tab("cheatsheet", "Cheats", Icons.AutoMirrored.Filled.List),
)

@Composable
fun GitTutApp(viewModel: GitTutViewModel = viewModel()) {
    val content = viewModel.content
    if (content == null) {
        LoadingScreen(
            loading = viewModel.loading,
            error = viewModel.error,
            onRetry = viewModel::load,
        )
        return
    }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = tabs.any { it.route == currentRoute }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = currentRoute == tab.route,
                            onClick = {
                                navController.navigate(tab.route) {
                                    popUpTo("learn") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) },
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "learn",
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
        ) {
            composable("learn") {
                LearnScreen(
                    viewModel = viewModel,
                    onOpenChapter = { chapterId -> navController.navigate("chapter/$chapterId") },
                    onOpenLesson = { lessonId -> navController.navigate("lesson/$lessonId") },
                )
            }

            composable("search") {
                SearchScreen(
                    viewModel = viewModel,
                    onOpenLesson = { lessonId -> navController.navigate("lesson/$lessonId") },
                )
            }

            composable("cheatsheet") {
                CheatSheetScreen(content.cheatSheet)
            }

            composable(
                route = "chapter/{chapterId}",
                arguments = listOf(navArgument("chapterId") { type = NavType.StringType }),
            ) { entry ->
                val chapterId = entry.arguments?.getString("chapterId").orEmpty()
                val chapter = content.chapter(chapterId)
                if (chapter == null) {
                    MissingScreen("Chapter not found", onBack = { navController.popBackStack() })
                } else {
                    ChapterScreen(
                        viewModel = viewModel,
                        chapter = chapter,
                        onBack = { navController.popBackStack() },
                        onOpenLesson = { lessonId -> navController.navigate("lesson/$lessonId") },
                        onOpenQuiz = { id -> navController.navigate("quiz/$id") },
                    )
                }
            }

            composable(
                route = "lesson/{lessonId}",
                arguments = listOf(navArgument("lessonId") { type = NavType.StringType }),
            ) { entry ->
                val lessonId = entry.arguments?.getString("lessonId").orEmpty()
                val lesson = content.lesson(lessonId)
                val chapter = lesson?.let { content.chapter(it.meta.chapterId) }
                if (lesson == null || chapter == null) {
                    MissingScreen("Lesson not found", onBack = { navController.popBackStack() })
                } else {
                    LessonScreen(
                        viewModel = viewModel,
                        chapter = chapter,
                        lesson = lesson,
                        nextLesson = viewModel.nextLessonInChapter(chapter.id, lessonId),
                        onBack = { navController.popBackStack() },
                        onOpenLesson = { nextId ->
                            navController.navigate("lesson/$nextId") {
                                popUpTo("lesson/$lessonId") { inclusive = true }
                            }
                        },
                    )
                }
            }

            composable(
                route = "quiz/{chapterId}",
                arguments = listOf(navArgument("chapterId") { type = NavType.StringType }),
            ) { entry ->
                val chapterId = entry.arguments?.getString("chapterId").orEmpty()
                val chapter = content.chapter(chapterId)
                if (chapter == null) {
                    MissingScreen("Quiz not found", onBack = { navController.popBackStack() })
                } else {
                    QuizScreen(
                        viewModel = viewModel,
                        chapter = chapter,
                        onBack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen(loading: Boolean, error: String?, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .padding(32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            if (error != null) {
                Text(
                    text = "Could not load GitTut",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                Button(onClick = onRetry) { Text("Try again") }
            } else if (loading) {
                CircularProgressIndicator()
                Text(
                    text = "Loading lessons…",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun MissingScreen(message: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(message, style = MaterialTheme.typography.titleMedium)
            Button(onClick = onBack) { Text("Go back") }
        }
    }
}
