package com.example.myappstudyverse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myappstudyverse.ui.theme.MyAppStudyVerseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppStudyVerseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(),       //in my composable there are other kid elements /composables
    horizontalAlignment = Alignment.CenterHorizontally,
     verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "StudyVerse",
            modifier = Modifier.padding(16.dp),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Text(
            text = "Willkommen zurück!"
        )
        Spacer(
        modifier = Modifier.height(16.dp)
        )
        Button(
            onClick = {
            }
        ) {
            Text("Los geht's")
        }
    }
}
