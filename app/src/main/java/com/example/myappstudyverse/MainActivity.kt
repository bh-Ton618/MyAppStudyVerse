package com.example.myappstudyverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myappstudyverse.ui.screens.HomeScreen
import com.example.myappstudyverse.ui.theme.MyAppStudyVerseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppStudyVerseTheme {
                HomeScreen()
                }
            }
        }
    }


