package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myappstudyverse.data.local.DatabaseProvider
import com.example.myappstudyverse.data.local.NoteRepository
import com.example.myappstudyverse.ui.viewmodel.NoteViewModel
import com.example.myappstudyverse.ui.viewmodel.NoteViewModelFactory
import kotlinx.coroutines.launch


@Composable
fun NoteDetailScreen(navController: NavHostController, noteId: Int?) {

    val isNewNote = noteId == null

    var noteTitle by remember { mutableStateOf("") }

    // Stores the text of the note while the user is editing it.
    var noteText by remember { mutableStateOf("") }

    // Stores whether the note is pinned.
    var isPinned by remember { mutableStateOf(false) }

    // Controls the short message shown saving an existing note.
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    val context = LocalContext.current

    val noteRepository = remember {
        NoteRepository(
            DatabaseProvider
                .getDatabase(context)
                .noteDao()
        )
    }

    val noteViewModel: NoteViewModel = viewModel(
        factory = NoteViewModelFactory(noteRepository)
    )

    val noteList by noteViewModel.notes.collectAsState()

    // Loads the notes from the local database when the screen is opened.
    LaunchedEffect(Unit) {
        noteViewModel.loadNotes()
    }

    // Loads the selected note into the editable fields when an existing note is opened.
    LaunchedEffect(noteList, noteId) {
        if (!isNewNote) {
            val existingNote = noteList.find { note ->
                note.id == noteId
            }

            if (existingNote != null) {
                noteTitle = existingNote.title
                noteText = existingNote.description
                isPinned = existingNote.isPinned
            }
        }
    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SnackbarHost(
                    hostState = snackbarHostState
                ) { snackbarData ->
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(48.dp)
                            .offset(y = 80.dp)
                            .border(
                                width = 0.5.dp,
                                color = Color(0xFF7C4DFF),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = snackbarData.visuals.message,
                            color = Color(0xFF7C4DFF)
                        )
                    }
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 35.dp
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    modifier = Modifier
                        .height(52.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF7C4DFF))
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            if (!isNewNote) {
                                val existingNote = noteList.find { note ->
                                    note.id == noteId
                                }

                                if (existingNote != null) {
                                    noteViewModel.deleteNote(existingNote)
                                }
                            }

                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                        Text(
                            text = "Delete",
                            color = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(22.dp)
                            .background(Color.White.copy(alpha = 0.5f))
                    )

                    TextButton(
                        onClick = {
                            val note = Note(
                                id = if (isNewNote) 0 else noteId,
                                title = noteTitle,
                                createdDate = if (isNewNote) {
                                    System.currentTimeMillis()
                                } else {
                                    noteList.find { note ->
                                        note.id == noteId
                                    }?.createdDate
                                        ?: System.currentTimeMillis()
                                },
                                description = noteText,
                                isPinned = isPinned
                            )

                            if (noteTitle.isNotBlank()) {
                                if (isNewNote) {
                                    noteViewModel.addNote(note)
                                    navController.popBackStack()
                                } else {
                                    noteViewModel.updateNote(note)
                                    coroutineScope.launch { snackbarHostState.showSnackbar("Saved") }
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "Save",
                            tint = Color.White
                        )
                        Text(
                            text = "Save",
                            color = Color.White
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 8.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {

            IconButton(
                onClick = {
                    if (noteTitle.isNotBlank()) {
                        if (isNewNote) {
                            val note = Note(
                                id = 0,
                                title = noteTitle,
                                createdDate = System.currentTimeMillis(),
                                description = noteText,
                                isPinned = isPinned
                            )

                            noteViewModel.addNote(note)
                        } else {
                            val existingNote = noteList.find { note ->
                                note.id == noteId
                            }

                            if (existingNote != null) {
                                val note = Note(
                                    id = noteId,
                                    title = noteTitle,
                                    createdDate = existingNote.createdDate,
                                    description = noteText,
                                    isPinned = isPinned
                                )

                                noteViewModel.updateNote(note)
                            }
                        }
                    }

                    navController.popBackStack()
                },

                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF0EBFF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.NoteAlt,
                        contentDescription = "Note",
                        tint = Color(0xFFA78BFA),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (isNewNote) "NEW NOTE" else "NOTE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFA78BFA)
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        isPinned = !isPinned
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PushPin,
                        contentDescription = if (isPinned) "Unpin note" else "Pin note",
                        tint = if (isPinned) Color(0xFFA78BFA) else Color.LightGray,
                        modifier = Modifier
                            .size(22.dp)
                            .rotate(35f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = noteTitle,
                onValueChange = { newNoteTitle ->
                    noteTitle = newNoteTitle
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (noteTitle.isEmpty() && isNewNote) {
                        Text(
                            text = "Title",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (!isNewNote) {
                val existingNote = noteList.find { note ->
                    note.id == noteId
                }

                if (existingNote != null) {
                    Text(
                        text = "Created: ${
                            java.text.SimpleDateFormat(
                                "dd.MM.yyyy, HH:mm",
                                java.util.Locale.getDefault()
                            ).format(java.util.Date(existingNote.createdDate))
                        }",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            BasicTextField(
                value = noteText,
                onValueChange = { newNoteText ->
                    noteText = newNoteText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp
                ),
                decorationBox = { innerTextField ->
                    if (noteText.isEmpty() && isNewNote) {
                        Text(
                            text = "Write your note here...",
                            fontSize = 16.sp,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}


