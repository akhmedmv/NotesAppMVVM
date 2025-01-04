@file:OptIn(ExperimentalMaterial3Api::class)

package com.akhmedmv.notesappmvvm.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.akhmedmv.notesappmvvm.navigation.NavRoute
import com.akhmedmv.notesappmvvm.ui.theme.NotesAppMVVMTheme
import com.akhmedmv.notesappmvvm.utils.Constants
import com.akhmedmv.notesappmvvm.utils.Constants.Keys.FIREBASE_DATABASE
import com.akhmedmv.notesappmvvm.utils.Constants.Keys.ROOM_DATABASE
import com.akhmedmv.notesappmvvm.utils.Constants.Keys.WHAT_WILL_WE_USE
import com.akhmedmv.notesappmvvm.utils.DB_TYPE
import com.akhmedmv.notesappmvvm.utils.LOGIN
import com.akhmedmv.notesappmvvm.utils.PASSWORD
import com.akhmedmv.notesappmvvm.utils.TYPE_FIREBASE
import com.akhmedmv.notesappmvvm.utils.TYPE_ROOM

@Composable
fun StartScreen(navController: NavHostController, viewModel: MainViewModel) {

    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    var skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
//    val coroutineScope = rememberCoroutineScope()

    var login by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var password by remember { mutableStateOf(Constants.Keys.EMPTY) }

    //Визуализация интерфейса
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isSheetOpen = false },
            sheetState = bottomSheetState,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            tonalElevation = BottomSheetDefaults.Elevation,
            scrimColor = Color.Black.copy(alpha = 0.32f) // Устанавливаем цвет затемняющего фона
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = Constants.Keys.LOG_IN,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Text(Constants.Keys.LOGIN_TEXT) },
                    isError = login.isEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(Constants.Keys.PASSWORD_TEXT) },
                    isError = password.isEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Button(
                    onClick = {
                        LOGIN = login
                        PASSWORD = password
                        viewModel.initDataBase(TYPE_FIREBASE) {
                            DB_TYPE = TYPE_FIREBASE
                            navController.navigate(NavRoute.Main.route)
                        }
                    },
                    enabled = login.isNotEmpty() && password.isNotEmpty(),
                    modifier = Modifier
                        .padding(top = 16.dp)
                ) {
                    Text(text = Constants.Keys.SIGN_IN)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = WHAT_WILL_WE_USE)
            Button(
                onClick = {
                    viewModel.initDataBase(TYPE_ROOM) {
                        DB_TYPE = TYPE_ROOM
                        navController.navigate(route = NavRoute.Main.route)
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = ROOM_DATABASE)
            }
            Button(
                onClick = {
                    isSheetOpen = true
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = FIREBASE_DATABASE)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewStartScreen() {
    NotesAppMVVMTheme {
        val context = LocalContext.current
        val mViewModel: MainViewModel =
            viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
        StartScreen(navController = rememberNavController(), mViewModel)
    }
}