package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myappstudyverse.data.local.DatabaseProvider
import com.example.myappstudyverse.data.local.NoteRepository
import com.example.myappstudyverse.ui.theme.Gold
import com.example.myappstudyverse.ui.theme.Gridline
import com.example.myappstudyverse.ui.theme.NavigationSurface
import com.example.myappstudyverse.ui.theme.OffWhite1
import com.example.myappstudyverse.ui.theme.OffWhite2
import com.example.myappstudyverse.ui.theme.PurplePrimary
import com.example.myappstudyverse.ui.theme.SpaceSurface
import com.example.myappstudyverse.ui.theme.textFieldInputHint
import com.example.myappstudyverse.ui.viewmodel.NoteViewModel
import com.example.myappstudyverse.ui.viewmodel.NoteViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class Note(
    val id: Int,
    val title: String,
    val createdDate: Long = System.currentTimeMillis(),
    val description: String,
    var isPinned: Boolean
)

enum class NoteSortOption {
    NEWEST,
    OLDEST,
    TITLE_ASC,
    TITLE_DESC
}

@Composable
fun NotesHeaderArtWork() {
    // TODO: Insert artwork here later
}

// Main notes screen with search, sorting and pinned management.
@Composable
fun NoteScreen(navController: NavHostController) {

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

    // Stores the current UI state for searching, sorting and pinned note visibility.
    var isSearchOpen by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isFilterMenuExpanded by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf(NoteSortOption.NEWEST) }
    var isPinnedSectionExpanded by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        noteViewModel.loadNotes()
    }

    val notesList by noteViewModel.notes.collectAsState()


    // Sorts notes while always keeping pinned notes at the top.
    val sortedNotesList = when (selectedSortOption) {
        NoteSortOption.NEWEST ->
            notesList.sortedWith(compareByDescending<Note> { note -> note.isPinned }.thenByDescending { note -> note.createdDate })

        NoteSortOption.OLDEST ->
            notesList.sortedWith(compareByDescending<Note> { note -> note.isPinned }.thenBy { note -> note.createdDate })

        NoteSortOption.TITLE_ASC ->
            notesList.sortedWith(compareByDescending<Note> { note -> note.isPinned }.thenBy { note -> note.title.lowercase() })

        NoteSortOption.TITLE_DESC ->
            notesList.sortedWith(compareByDescending<Note> { note -> note.isPinned }.thenByDescending { note -> note.title.lowercase() })
    }

// Filters notes based on the entered search query.
    val filteredNotesList = sortedNotesList.filter { note ->
        note.title.contains(searchText, ignoreCase = true) ||
                note.description.contains(searchText, ignoreCase = true)
    }




    Scaffold(
        // Placeholder button for creating new notes in a future version.
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("noteDetail/new") },
                modifier = Modifier.offset(y = 24.dp),
                shape = CircleShape,
                containerColor = PurplePrimary
            ) {
                Icon(
                    imageVector = Icons.Outlined.NoteAlt,
                    contentDescription = "Add note",
                    tint = Color.White
                )
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
                    top = 24.dp,
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            NotesHeaderArtWork()

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // this needs to be changed because one more icon added in the same row
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "My Notes",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = OffWhite1
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            isSearchOpen = !isSearchOpen
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .offset(y = 2.dp),
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = OffWhite2
                        )

                    }
                    IconButton(
                        onClick = {
                            isFilterMenuExpanded = !isFilterMenuExpanded
                        }
                    )
                    {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .offset(y = 2.dp),
                            imageVector = Icons.Outlined.FilterList,
                            contentDescription = "Sort",
                            tint = OffWhite2
                        )
                    }
                    DropdownMenu(
                        expanded = isFilterMenuExpanded,
                        onDismissRequest = {
                            isFilterMenuExpanded = false
                        },
                        containerColor = NavigationSurface,
                        border = BorderStroke(width = 1.dp, color = Gridline)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Newest created", color = OffWhite2) },
                            onClick = {
                                selectedSortOption = NoteSortOption.NEWEST
                                isFilterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Oldest created", color = OffWhite2) },
                            onClick = {
                                selectedSortOption = NoteSortOption.OLDEST
                                isFilterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Title (A-Z)", color = OffWhite2) },
                            onClick = {
                                selectedSortOption = NoteSortOption.TITLE_ASC
                                isFilterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Title (Z-A)", color = OffWhite2) },
                            onClick = {
                                selectedSortOption = NoteSortOption.TITLE_DESC
                                isFilterMenuExpanded = false
                            }
                        )

                    }
                }
            }

            // Displays the search when search mode is enabled.
            if (isSearchOpen) {
                BasicTextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            width = 0.4.dp,
                            color = OffWhite2,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .padding(horizontal = 14.dp),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = OffWhite2
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchText.isEmpty()) {
                                Text(
                                    text = "Search notes...",
                                    color = textFieldInputHint
                                )
                            }

                            innerTextField()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Separates pinned regular notes for independent display.
            val pinnedNotes = filteredNotesList.filter { note -> note.isPinned }
            val unpinnedNotes = filteredNotesList.filter { note -> !note.isPinned }

            // Allows the pinned notes section to be expanded or collapsed.
            if (pinnedNotes.isNotEmpty()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { isPinnedSectionExpanded = !isPinnedSectionExpanded },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = "Pinned",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = OffWhite1
                    )
                    Icon(
                        imageVector =
                            if (isPinnedSectionExpanded)
                                Icons.Filled.KeyboardArrowDown
                            else
                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Toggle pinned notes"
                    )

                }
            }


            // Displays all notes in a scrollable list.
            // LazyColumn renders only visible items to improve performance.
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                if (isPinnedSectionExpanded) {
                    items(
                        items = pinnedNotes,
                        key = { note -> note.id }
                    ) { note ->
                        NoteCard(
                            note = note,
                            onNoteClick = {
                                navController.navigate("noteDetail/${note.id}")
                            },
                            onPinClick = {
                                noteViewModel.updateNote(
                                    note.copy(isPinned = false)
                                )
                            },
                            onDeleteClick = {
                                noteViewModel.deleteNote(note)
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                if (unpinnedNotes.isNotEmpty()) {
                    item {
                        Text(
                            text = "Notes",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = OffWhite1
                        )
                    }
                }
                items(
                    items = unpinnedNotes,
                    key = { note -> note.id }
                ) { note ->
                    NoteCard(
                        note = note,
                        onNoteClick = {
                            navController.navigate("noteDetail/${note.id}")
                        },
                        onPinClick = {
                            noteViewModel.updateNote(
                                note.copy(isPinned = true)
                            )
                        },
                        onDeleteClick = {
                            noteViewModel.deleteNote(note)
                        }
                    )
                }


            }
        }
    }
}


// Reusable card displaying a single note.
@Composable
fun NoteCard(
    note: Note,
    onNoteClick: () -> Unit,
    onPinClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var isContextMenuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onNoteClick,
                onLongClick = {
                    isContextMenuExpanded = true
                }
            ),
        colors = CardDefaults.cardColors(containerColor = SpaceSurface),
        border = BorderStroke(width = 1.dp, color = Gridline)
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (note.isPinned) {
                    Icon(
                        imageVector = Icons.Default.PushPin,
                        contentDescription = "Pinned Note",
                        tint = Gold,
                        modifier = Modifier
                            .size(18.dp)
                            .rotate(20f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Column {
                    Text(
                        text = note.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = OffWhite2
                    )
                    Text(
                        text = SimpleDateFormat(
                            "dd.MM.yyyy, HH:mm",
                            Locale.getDefault()
                        ).format(Date(note.createdDate)),
                        fontSize = 14.sp,
                        color = OffWhite2
                    )
                }
            }
            DropdownMenu(
                expanded = isContextMenuExpanded,
                onDismissRequest = {
                    isContextMenuExpanded = false
                },
                offset = DpOffset(
                    x = (-40).dp,
                    y = (-120).dp
                ),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = OffWhite1.copy(alpha = 0.4f)
                ),
                containerColor = NavigationSurface
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            onPinClick()
                            isContextMenuExpanded = false
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PushPin,
                        contentDescription = null,
                        tint = Color(0xFFA78BFA),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (note.isPinned) "Unpin" else "Pin",
                        color = OffWhite2
                    )
                }
                Row(
                    modifier = Modifier
                        .clickable {
                            onDeleteClick()
                            isContextMenuExpanded = false
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color(0xFFA78BFA),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Delete",
                        color = OffWhite2
                    )
                }
            }
        }
    }
}