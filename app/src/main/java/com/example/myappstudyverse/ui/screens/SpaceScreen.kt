package com.example.myappstudyverse.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myappstudyverse.BuildConfig
import com.example.myappstudyverse.model.ApodResponse
import com.example.myappstudyverse.network.RetrofitInstance
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


// Displays NASA's Astronomy Picture of the Day retrieved from the NASA APOD API.
@Composable
fun SpaceScreen(navController: NavHostController) {

    // Stores the API response and possible error messages.
    var apodResponse by remember { mutableStateOf<ApodResponse?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    // Requests NASA's Astronomy Picture of the Day once when the screen is opened.
    LaunchedEffect(Unit) {
        try {

            // Fetches the current Astronomy Picture of the Day from the NASA API.
            val response = RetrofitInstance.api.getPictureOfTheDay(BuildConfig.NASA_API_KEY)
            apodResponse = response
            // Handles common network and server errors with user-friendly messages.
        } catch (e: SocketTimeoutException) {

            errorMessage =
                "The request timed out.\nPlease try again."

        } catch (e: UnknownHostException) {

            errorMessage =
                "No internet connection.\nPlease check your internet connection."

        } catch (e: HttpException) {

            errorMessage =
                "NASA's servers are currently unavailable.\nPlease try again later."

        } catch (e: Exception) {

            errorMessage =
                "Something went wrong.\nPlease try again."
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = 20.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp)
                    .size(14.dp),
                tint = Color(0XFF7C4DFF)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "NASA PICTURE OF THE DAY",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp,
                    color = Color(0XFF7C4DFF)
                )

                Spacer(modifier = Modifier.size(48.dp))
            }
        }

        // Displays an error screen if the API request fails.
        if (errorMessage != null) {

            val messageToUser = errorMessage ?: return

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.CloudOff,
                        contentDescription = null,
                        tint = Color(0xFF7C4DFF),
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = messageToUser,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 24.sp
                    )
                }
            }
            // Displays a loading indicator while waiting for the API response.
        } else if (apodResponse == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0XFF7C4DFF))
            }

            //Displays the retrieved Astronomy Picture of the Day.
        } else {

            val loadedApodResponse = apodResponse ?: return
            if (loadedApodResponse.media_type == "image") {

                AsyncImage(
                    model = loadedApodResponse.url,
                    contentDescription = loadedApodResponse.title,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            } else if (loadedApodResponse.media_type == "video") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Today's NASA content is a video. Please click the button down below to watch it on NASA.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }

            }
            val formattedDate = loadedApodResponse.date.let { date ->
                val parsedDate = LocalDate.parse(date)

                parsedDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH))
            }

            // Removes unnecessary whitespace from the explanation text.
            val formattedExplanation =
                loadedApodResponse.explanation.replace(Regex("\\s+"), " ").trim()


            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = loadedApodResponse.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = formattedDate,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0XFF7C4DFF)
                )
                loadedApodResponse.copyright?.let { copyright ->
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = "© $copyright", maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = formattedExplanation)

                Spacer(modifier = Modifier.height(28.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

                    // Opens the official NASA APOD website in the device's web browser.
                    OutlinedButton(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://apod.nasa.gov/apod/astropix.html")
                            )
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF7C4DFF)),
                        border = BorderStroke(1.dp, Color(0xFF7C4DFF))
                    ) {
                        Text("Learn More on NASA")
                        Spacer(modifier = Modifier.height(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                            contentDescription = "NASA",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

}
