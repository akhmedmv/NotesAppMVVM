package com.akhmedmv.notesappmvvm.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.akhmedmv.notesappmvvm.navigation.NavRoute
import com.akhmedmv.notesappmvvm.ui.theme.NotesAppMVVMTheme

@Composable
fun MainScreen(navController: NavHostController) {
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
            NoteItem(title = "Sd", subtitle = "dfj", navController)
            NoteItem(title = "Sd", subtitle = "dfj", navController)
            NoteItem(title = "Sd", subtitle = "dfj", navController)
            NoteItem(title = "Sd", subtitle = "dfj", navController)
        }
    }
}

@Composable
fun NoteItem(
    title: String,
    subtitle: String,
    navController: NavHostController,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                navController.navigate(NavRoute.Note.route)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    )
    {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = subtitle)
        }
    }
}


@Preview
@Composable
fun PreviewMainScreen() {
    NotesAppMVVMTheme {
        MainScreen(navController = rememberNavController())
    }
}