package com.example.myappstudyverse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Note(
    val id: Int,
    val title: String,
    val createdDate: String,
    val description: String,
    var isPinned: Boolean
)


@Composable
fun NotesHeaderArtWork() {
    //TODO: Insert artwork here later
}

@Composable
fun NotesScreen() {

    var isSearchOpen by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }


    val notesList = remember {
        mutableStateListOf(
            Note(id = 1, "Note 1", "Created: 02.08.2026", "kasf", isPinned = true),
            Note(
                id = 2,
                "Note 2",
                "Created: 29.07.2026",
                "fsfwefwe",
                isPinned = false
            ),
            Note(
                id = 3,
                "Note 3",
                "Created: 01.08.2026",
                "wefwefwef",
                isPinned = false
            ),

            Note(
                id = 4,
                "Note 4",
                "Created: 15.07.2026",
                "wefwefwef",
                isPinned = false

            ),
            Note(id = 5, "Note 5", "Created: 21.07.2026", "wfwef", isPinned = false),
            Note(
                id = 6,
                "Note 6",
                "Created: 22.07.2026",
                "Lwefwefw",
                isPinned = false
            ),

            Note(id = 7, "Note 7", "Created: 22.07.2026", "wefwef", isPinned = false),
            Note(
                id = 8,
                "Note 8",
                "Created: 22.07.2026",
                "wefwef",
                isPinned = false
            ),

            Note(id = 9, "Note 9", "Created: 22.07.2026", "efwef", isPinned = false),
            Note(
                id = 10,
                "Note 10",
                "Created: 22.07.2026",
                "efwef",
                isPinned = false

            )
        )
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.offset(y = 24.dp),
                shape = CircleShape,
                containerColor = Color(0xFFA78BFA)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add task",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
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
                        //another filter Icon needed here ! <---
                    }
                    IconButton(
                        onClick = {
                            //TODO: // to sort
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .offset(y = 2.dp),
                            imageVector = Icons.Outlined.FilterList,
                            contentDescription = "Sort"
                        )
                    }
                }
            }

            if (isSearchOpen) {
                // TODO: //
            }
            Spacer(modifier = Modifier.height(16.dp))


            // Scrollable list of all notes
            // Only visible elements will be rendered (Lazy Loading)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(
                    items = notesList,   // maybe we need this in case later we filter by the filter icon ? example by create date, title and more
                    key = { note -> note.id }
                ) { note ->
                    NoteCard(
                        note = note,
                        onNoteClick = {
                            //TODO: //
                        }
                    )
                }
            }
        }
    }
}


//Displays a single noteCard
@Composable
fun NoteCard(
    note: Note,
    onNoteClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = note.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = note.createdDate, fontSize = 14.sp)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Open"
            )
        }
    }
}






