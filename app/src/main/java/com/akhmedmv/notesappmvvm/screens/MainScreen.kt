package com.akhmedmv.notesappmvvm.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.akhmedmv.notesappmvvm.MainViewModel
import com.akhmedmv.notesappmvvm.MainViewModelFactory
import com.akhmedmv.notesappmvvm.model.Note
import com.akhmedmv.notesappmvvm.navigation.NavRoute
import com.akhmedmv.notesappmvvm.ui.theme.NotesAppMVVMTheme
import com.akhmedmv.notesappmvvm.utils.Constants
import com.akhmedmv.notesappmvvm.utils.DB_TYPE
import com.akhmedmv.notesappmvvm.utils.TYPE_FIREBASE
import com.akhmedmv.notesappmvvm.utils.TYPE_ROOM

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {
    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate(NavRoute.Add.route) }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Icons",
                tint = Color.White
            )
        }
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (notes.isEmpty()) {
                Text(
                    text = "Нет заметок",
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            } else {
                LazyColumn {
                    items(notes) { note ->
                        NoteItem(note, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, navController: NavHostController) {
    val noteId = when (DB_TYPE) {
        TYPE_FIREBASE -> note.firebaseId
        TYPE_ROOM -> note.id
        else -> Constants.Keys.EMPTY
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                navController.navigate(NavRoute.Note.route + "/${noteId}")
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = note.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = note.subtitle)
        }
    }
}


@Preview
@Composable
fun PreviewMainScreen() {
    NotesAppMVVMTheme {
        val context = LocalContext.current
        val mViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        MainScreen(navController = rememberNavController(), mViewModel)
    }
}