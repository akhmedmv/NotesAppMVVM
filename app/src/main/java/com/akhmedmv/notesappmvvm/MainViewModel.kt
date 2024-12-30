package com.akhmedmv.notesappmvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akhmedmv.notesappmvvm.database.room.AppRoomDateBase
import com.akhmedmv.notesappmvvm.database.room.repository.RoomRepository
import com.akhmedmv.notesappmvvm.utils.REPOSITORY
import com.akhmedmv.notesappmvvm.utils.TYPE_FIREBASE
import com.akhmedmv.notesappmvvm.utils.TYPE_ROOM

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val context = application

    fun initDataBase(type: String, onSuccess: () -> Unit) {
        Log.d("checkData", "MainViewModel init DataBase with type: $type")
        when (type) {
            TYPE_ROOM -> {
                val dao = AppRoomDateBase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }

            TYPE_FIREBASE -> {

            }
        }
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}