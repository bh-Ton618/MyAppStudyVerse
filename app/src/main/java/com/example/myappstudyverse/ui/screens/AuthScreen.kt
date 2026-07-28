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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myappstudyverse.R
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException


enum class AuthState {
    EMAIL_ONLY,
    LOGIN,
    REGISTER,
    VERIFY_SENT,
    EMAIL_NOT_VERIFIED,
    LOGIN_SUCCESS
}

@Composable
fun AuthScreen(navController: NavHostController) {

    var authState by remember { mutableStateOf(AuthState.EMAIL_ONLY) }

    var emailInput by remember { mutableStateOf("") }
    var userNameInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

// The following 3 variables below are specifically for the use cases that a user encounters an exception error (Firebase Exceptions) ->
    var loginErrorMessage by remember { mutableStateOf("") }
    var registrationErrorMessage by remember { mutableStateOf("") }
    var verificationEmailErrorMessage by remember { mutableStateOf("") }

    // The following variable is used for user input validation. Input fields are not allowed to be empty! ->
    var inputValidationMessage by remember { mutableStateOf("") }


    val fireBaseAuth = FirebaseAuth.getInstance()

    val topSpacing = when (authState) {
        AuthState.EMAIL_ONLY ->
            if (inputValidationMessage.isNotEmpty()) 20.dp
            else 50.dp

        AuthState.LOGIN ->
            if (loginErrorMessage.isNotEmpty() || inputValidationMessage.isNotEmpty())
                80.dp
            else 110.dp

        AuthState.REGISTER ->
            if (registrationErrorMessage.isNotEmpty() || inputValidationMessage.isNotEmpty()) 20.dp
            else 50.dp

        AuthState.VERIFY_SENT -> 400.dp
        AuthState.EMAIL_NOT_VERIFIED ->
            if (verificationEmailErrorMessage.isNotEmpty()) 280.dp
            else 320.dp

        AuthState.LOGIN_SUCCESS -> 500.dp
    }

    Box(modifier = Modifier.fillMaxSize()) {

        val background = when (authState) {
            AuthState.EMAIL_NOT_VERIFIED -> R.drawable.auth_email_not_verified_background
            AuthState.LOGIN_SUCCESS -> R.drawable.auth_email_verified_background
            else -> R.drawable.auth_background
        }

        val showBranding = when (authState) {
            AuthState.VERIFY_SENT,
            AuthState.EMAIL_NOT_VERIFIED,
            AuthState.LOGIN_SUCCESS -> false

            else -> true
        }

        Image(
            painter = painterResource(id = background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(140.dp))

            if (showBranding) {
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
            }

            // This Spacer is dynamic and changes height based on the current AuthState ->
            Spacer(modifier = Modifier.height(topSpacing))


            when (authState) {
                AuthState.EMAIL_ONLY -> {
                    if (inputValidationMessage.isNotEmpty()) {
                        Text(
                            text = inputValidationMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                    EmailTextField(
                        value = emailInput,
                        onValueChange = { newEmail ->
                            emailInput = newEmail
                            inputValidationMessage = ""
                        },
                        isError = inputValidationMessage.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AuthButton(text = "Log In", onClick = {
                        if (emailInput.isBlank()) {
                            inputValidationMessage = "Please enter your email address."
                        } else {
                            inputValidationMessage = ""
                            authState = AuthState.LOGIN
                        }
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                    AuthButton(text = "Create Account", onClick = {
                        if (emailInput.isBlank()) {
                            inputValidationMessage = "Please enter your email address."
                        } else {
                            inputValidationMessage = ""
                            authState = AuthState.REGISTER
                        }
                    })
                }

                AuthState.LOGIN -> {
                    if (loginErrorMessage.isNotEmpty() || inputValidationMessage.isNotEmpty()) {
                        Text(
                            text = if (inputValidationMessage.isNotEmpty()) {
                                inputValidationMessage
                            } else {
                                loginErrorMessage
                            },
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 22.dp, end = 22.dp, bottom = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    PWTextField(
                        value = passwordInput,
                        onValueChange = { newPassword ->
                            passwordInput = newPassword
                            inputValidationMessage = ""
                        },
                        isError = inputValidationMessage.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AuthButton(text = "Log In", onClick = {
                        if (passwordInput.isBlank()) {
                            inputValidationMessage = "Please enter your password."
                            loginErrorMessage = ""
                        } else {
                            inputValidationMessage = ""

                            FirebaseAuth.getInstance()
                                .signInWithEmailAndPassword(emailInput, passwordInput)
                                .addOnCompleteListener { login ->
                                    if (login.isSuccessful) {
                                        val currentUser = FirebaseAuth.getInstance().currentUser
                                        currentUser
                                            ?.reload()
                                            ?.addOnCompleteListener { userReload ->
                                                if (userReload.isSuccessful) {
                                                    if (currentUser.isEmailVerified) {
                                                        authState = AuthState.LOGIN_SUCCESS
                                                    } else {
                                                        authState = AuthState.EMAIL_NOT_VERIFIED
                                                    }
                                                }
                                            }
                                    } else {
                                        when (login.exception) {
                                            is FirebaseAuthInvalidCredentialsException -> {
                                                loginErrorMessage =
                                                    "Incorrect email or password. Please try again."
                                            }

                                            is FirebaseAuthInvalidUserException -> {
                                                loginErrorMessage =
                                                    "No account found. Please create an account."
                                            }

                                            else -> {
                                                loginErrorMessage =
                                                    "Login failed. Please try again."
                                            }
                                        }
                                    }
                                }
                        }
                    })
                    Spacer(modifier = Modifier.height(6.dp))
                    AuthHyperlink(
                        linkText = "<- Back",
                        underlined = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 75.dp),
                        onClick = {
                            passwordInput = ""
                            loginErrorMessage = ""
                            inputValidationMessage = ""
                            authState = AuthState.EMAIL_ONLY
                        })
                    AuthHyperlink(
                        text = "Don't have an account? ",
                        linkText = "Create one",
                        onClick = { authState = AuthState.REGISTER })
                }


                AuthState.REGISTER -> {
                    if (registrationErrorMessage.isNotEmpty() || inputValidationMessage.isNotEmpty()) {
                        Text(
                            text = if (inputValidationMessage.isNotEmpty()) {
                                inputValidationMessage
                            } else {
                                registrationErrorMessage
                            },
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 22.dp, end = 22.dp, bottom = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    UserNameTextField(
                        value = userNameInput,
                        onValueChange = { newUserName ->
                            userNameInput = newUserName
                            inputValidationMessage = ""
                        },
                        isError = inputValidationMessage.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PWTextField(
                        value = passwordInput,
                        onValueChange = { newPassword ->
                            passwordInput = newPassword
                            inputValidationMessage = ""
                        },
                        isError = inputValidationMessage.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AuthButton(
                        text = "Create Account",
                        onClick = {
                            if (userNameInput.isBlank() && passwordInput.isBlank()) {
                                inputValidationMessage = "Please enter a username and password."
                                registrationErrorMessage = ""
                            } else if (userNameInput.isBlank()) {
                                inputValidationMessage = "Please enter a username."
                                registrationErrorMessage = ""
                            } else if (passwordInput.isBlank()) {
                                inputValidationMessage = "Please enter a password."
                                registrationErrorMessage = ""
                            } else {

                                fireBaseAuth.createUserWithEmailAndPassword(
                                    emailInput,
                                    passwordInput
                                )
                                    .addOnCompleteListener { registration ->
                                        if (registration.isSuccessful) {
                                            FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                                                ?.addOnCompleteListener { emailSendVerification ->
                                                    if (emailSendVerification.isSuccessful) {
                                                        authState = AuthState.VERIFY_SENT
                                                    }
                                                }
                                        } else {
                                            when (registration.exception) {
                                                is FirebaseAuthUserCollisionException -> {
                                                    registrationErrorMessage =
                                                        "An account with this email already exists. Please log in instead."
                                                }

                                                else -> {
                                                    registrationErrorMessage =
                                                        "Registration failed. Please try again."
                                                }
                                            }
                                        }
                                    }
                            }
                        })
                    Spacer(modifier = Modifier.height(16.dp))
                    AuthHyperlink(
                        text = "Already have an account? ",
                        linkText = "Log In",
                        onClick = { authState = AuthState.EMAIL_ONLY })
                }

                AuthState.VERIFY_SENT -> {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Verification Email",
                        tint = Color.White,
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "We've sent you a verification email",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Please verify your email to continue.",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(120.dp))
                    AuthButton(
                        text = "I've verified my email",
                        onClick = {
                            val currentUser = FirebaseAuth.getInstance().currentUser
                            currentUser
                                ?.reload()
                                ?.addOnCompleteListener { userReload ->
                                    if (userReload.isSuccessful) {
                                        if (currentUser.isEmailVerified) {
                                            authState = AuthState.LOGIN_SUCCESS
                                        } else {
                                            authState = AuthState.EMAIL_NOT_VERIFIED
                                        }
                                    }
                                }
                        })
                }

                AuthState.EMAIL_NOT_VERIFIED -> {
                    if (verificationEmailErrorMessage.isNotEmpty()) {
                        Text(
                            text = verificationEmailErrorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 22.dp),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Text(
                        text = "Email not verified",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Please verify your email to continue. If you haven't received the email yet, you can resend it below.",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(250.dp))
                    AuthButton(
                        text = "Resend Verification email",
                        onClick = {
                            FirebaseAuth.getInstance()
                                .currentUser
                                ?.sendEmailVerification()
                                ?.addOnCompleteListener { resendVerificationEmail ->
                                    if (resendVerificationEmail.isSuccessful) {
                                        authState = AuthState.VERIFY_SENT
                                    } else {
                                        when (resendVerificationEmail.exception) {
                                            is FirebaseAuthInvalidUserException -> {
                                                verificationEmailErrorMessage =
                                                    "Your session has expired. Please log in again."
                                            }

                                            is FirebaseTooManyRequestsException ->
                                                verificationEmailErrorMessage =
                                                    "Too many request.Please wait a moment and try again."

                                            else -> {
                                                verificationEmailErrorMessage =
                                                    "We couldn't send a verification email. Please try again."
                                            }
                                        }


                                    }
                                }
                        })
                    Spacer(modifier = Modifier.height(16.dp))
                    AuthHyperlink(
                        linkText = "Back to Login",
                        onClick = { authState = AuthState.LOGIN })
                }

                AuthState.LOGIN_SUCCESS -> {
                    Text(
                        text = "Welcome Back, Anna! \uD83D\uDE80",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Let's continue your journey.",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(90.dp))
                    AuthButton(
                        text = "Go to Dashboard",
                        onClick = {
                            navController.navigate("dashboard") {
                                popUpTo("auth") {
                                    inclusive = true
                                }
                            }
                        })
                }
            }

        }

    }
}


@Composable
fun EmailTextField(value: String, onValueChange: (String) -> Unit, isError: Boolean = false) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null
            )
        },
        placeholder = {
            Text("Email")

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
}


@Composable
fun UserNameTextField(value: String, onValueChange: (String) -> Unit, isError: Boolean = false) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        placeholder = {
            Text("Username")

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

}


@Composable
fun PWTextField(value: String, onValueChange: (String) -> Unit, isError: Boolean = false) {

    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = isError,
        visualTransformation =
            if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                if (passwordVisible) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Hide password"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "Show password"
                    )

                }
            }
        },
        placeholder = {
            Text("Password")

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

}


@Composable
fun AuthButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
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
        Text(text)
    }
}


@Composable
fun AuthHyperlink(
    modifier: Modifier = Modifier, text: String = "", linkText: String, underlined: Boolean = true, onClick: () -> Unit) {

    val hyperLinkText = buildAnnotatedString {
        append(text)

        withLink(
            LinkAnnotation.Clickable(
                tag = "link",
                linkInteractionListener = {
                    onClick()
                }
            )
        ) {
            withStyle(
                SpanStyle(
                    color = Color(0xFFA78BFA),
                    textDecoration =
                        if (underlined)
                            TextDecoration.Underline
                        else
                            TextDecoration.None
                )
            ) {
                append(linkText)
            }
        }
    }
    Text(text = hyperLinkText, color = Color.LightGray, modifier = modifier)
}
