package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myappstudyverse.R

@Composable
fun AuthScreen(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.auth_background1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(140.dp))

            Image(
                painter = painterResource(id = R.drawable.studyverse_logo),
                contentDescription = "StudyVerse Logo",
                modifier = Modifier.size(350.dp)
            )

            Text(
                modifier = Modifier.offset(y = (-110).dp), text =
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(color = Color.White)
                        ) {
                            append("Study")
                        }

                        withStyle(
                            style = SpanStyle(color = Color(0xFFA78BFA))
                        ) {
                            append("Verse")
                        }
                    },
                fontSize = 36.sp,
                fontWeight = FontWeight.Normal
            )

            Text(text = "Plan.Study.Achieve.", color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "✦", color = Color(0xFFA78BFA), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "Your universe of productivity.", color = Color.White, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp),
                value = "",
                onValueChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text("E-mail")

                },
                colors = OutlinedTextFieldDefaults.colors(

                    focusedBorderColor = Color(0XFFDCD6FF).copy(alpha = 0.2f),
                    unfocusedBorderColor = Color(0XFFDCD6FF).copy(alpha = 0.2f),

                    focusedTextColor = Color(0XFFDCD6FF).copy(alpha = 0.3f),
                    unfocusedTextColor = Color(0XFFDCD6FF).copy(alpha = 0.3f),

                    focusedLeadingIconColor = Color(0XFFDCD6FF).copy(alpha = 0.25f),
                    unfocusedLeadingIconColor = Color(0XFFDCD6FF).copy(alpha = 0.25f),

                    focusedPlaceholderColor = Color(0XFFDCD6FF).copy(alpha = 0.4f),
                    unfocusedPlaceholderColor = Color(0XFFDCD6FF).copy(alpha = 0.4f)
                ),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8F6EF5),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Continue")
            }

        }

    }
}