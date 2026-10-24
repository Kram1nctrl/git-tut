package com.kram1nctrl.gittut

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kram1nctrl.gittut.ui.GitTutApp
import com.kram1nctrl.gittut.ui.theme.GitTutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitTutTheme {
                GitTutApp()
            }
        }
    }
}
