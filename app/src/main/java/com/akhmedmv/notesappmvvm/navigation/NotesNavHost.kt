package com.akhmedmv.notesappmvvm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.akhmedmv.notesappmvvm.screens.Add
import com.akhmedmv.notesappmvvm.screens.Main
import com.akhmedmv.notesappmvvm.screens.Note
import com.akhmedmv.notesappmvvm.screens.Start

sealed class NavRoute(val route: String) {
    object Start : NavRoute("start_screen")
    object Main : NavRoute("main_screen")
    object Add : NavRoute("add_screen")
    object Note : NavRoute("note_screen")
}

@Composable
fun NotesNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route) {
        composable(NavRoute.Start.route) { Start(navController) }
        composable(NavRoute.Main.route) { Main(navController) }
        composable(NavRoute.Add.route) { Add(navController) }
        composable(NavRoute.Note.route) { Note(navController) }
    }
}