package com.akhmedmv.notesappmvvm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akhmedmv.notesappmvvm.MainViewModel
import com.akhmedmv.notesappmvvm.screens.AddScreen
import com.akhmedmv.notesappmvvm.screens.MainScreen
import com.akhmedmv.notesappmvvm.screens.NoteScreen
import com.akhmedmv.notesappmvvm.screens.StartScreen

sealed class NavRoute(val route: String) {
    object Start : NavRoute("start_screen")
    object Main : NavRoute("main_screen")
    object Add : NavRoute("add_screen")
    object Note : NavRoute("note_screen")
}

@Composable
fun NotesNavHost(mViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoute.Start.route) {
        composable(NavRoute.Start.route) { StartScreen(navController, viewModel = mViewModel) }
        composable(NavRoute.Main.route) { MainScreen(navController, viewModel = mViewModel) }
        composable(NavRoute.Add.route) { AddScreen(navController, viewModel = mViewModel) }
        composable(NavRoute.Note.route) { NoteScreen(navController, viewModel = mViewModel) }
    }
}