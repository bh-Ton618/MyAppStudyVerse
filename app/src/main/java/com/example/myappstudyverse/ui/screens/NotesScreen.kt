package com.example.myappstudyverse.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Note(
    val id: Int,
    val title: String,
    val createdDate: String,
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
fun NotesScreen() {

    // Stores the current UI state for searching, sorting and pinned note visibility.
    var isSearchOpen by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isFilterMenuExpanded by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf(NoteSortOption.NEWEST) }
    var isPinnedSectionExpanded by remember { mutableStateOf(true) }


    // Sample note data used for demonstrating note management features.
    val notesList = remember {
        mutableStateListOf(
            Note(id = 1, "About Biology", "02.08.2026", "Learned today that ...", isPinned = true),
            Note(
                id = 2,
                "Singularity",
                "29.07.2026",
                "Einstein: What does it actually mean?",
                isPinned = true
            ),
            Note(
                id = 3,
                "MultiVerse",
                "01.08.2026",
                "Watched a documantary about that and ...",
                isPinned = false
            ),

            Note(
                id = 4,
                "Legal things about rentals",
                "15.07.2026",
                "landlord told me...",
                isPinned = false

            ),
            Note(id = 5, "Food and Cafes", "21.07.2026", "Berlin... location", isPinned = false),
            Note(
                id = 6,
                "travel destinations",
                "22.07.2026",
                "Singapur, Hongkong, Italy",
                isPinned = false
            ),

            Note(
                id = 7,
                "Travel plans",
                "22.07.2026",
                "Start:We first need to...",
                isPinned = true
            ),
            Note(
                id = 8,
                "secrets ",
                "22.07.2026",
                "...",
                isPinned = false
            ),

            Note(id = 9, "Inform Mom about", "22.07.2026", "about the house and", isPinned = false),
            Note(
                id = 10,
                "Random",
                "22.07.2026",
                "random...",
                isPinned = false

            )
        )
    }
    // Sorts notes while always keping pinned notes at the top.
    // TODO: Replace ID sorting with createdTimeStamp after introducing persistent storage.
    val sortedNotesList = when (selectedSortOption) {
        NoteSortOption.NEWEST ->
            notesList.sortedWith(compareByDescending<Note> { note -> note.isPinned }.thenByDescending { note -> note.id })

        NoteSortOption.OLDEST ->
            notesList.sortedWith(compareByDescending<Note> { note -> note.isPinned }.thenBy { note -> note.id })

        NoteSortOption.TITLE_ASC ->
            notesList.sortedWith(compareByDescending<Note> { note -> note.isPinned }.thenBy { note -> note.title })

        NoteSortOption.TITLE_DESC ->
            notesList.sortedWith(compareByDescending<Note> { note -> note.isPinned }.thenByDescending { note -> note.title })
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
                onClick = {},
                modifier = Modifier.offset(y = 24.dp),
                shape = CircleShape,
                containerColor = Color(0xFFA78BFA)
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
                    fontWeight = FontWeight.Bold
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
                            contentDescription = "Search"
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
                            contentDescription = "Sort"
                        )
                    }
                    DropdownMenu(
                        expanded = isFilterMenuExpanded,
                        onDismissRequest = {
                            isFilterMenuExpanded = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Newest") },
                            onClick = {
                                selectedSortOption = NoteSortOption.NEWEST
                                isFilterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Oldest") },
                            onClick = {
                                selectedSortOption = NoteSortOption.OLDEST
                                isFilterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Title (A-Z)") },
                            onClick = {
                                selectedSortOption = NoteSortOption.TITLE_ASC
                                isFilterMenuExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Title (Z-A)") },
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
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText -> searchText = newText },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(18.dp),
                    placeholder = { Text("Search notes...") },
                    singleLine = true
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
                    Text(text = "Pinned", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
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
                                // TODO: Navigate to the note detail screen.
                            },
                            onPinClick = { note.isPinned = false },
                            onDeleteClick = { notesList.remove(note) }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                item {
                    Text(
                        text = "Notes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                items(
                    items = unpinnedNotes,
                    key = { note -> note.id }
                ) { note ->
                    NoteCard(
                        note = note,
                        onNoteClick = {
                            // TODO: Navigate to note detail screen
                        },
                        onPinClick = { note.isPinned = true },
                        onDeleteClick = { notesList.remove(note) }
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
            )
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
                        tint = Color(0xFFA78BFA),
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
                        fontSize = 18.sp
                    )
                    Text(
                        text = note.createdDate,
                        fontSize = 14.sp
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
                shape = RoundedCornerShape(20.dp)
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
                        text = if (note.isPinned) "Unpin" else "Pin"
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
                        text = "Delete"
                    )
                }
            }
        }
    }
}