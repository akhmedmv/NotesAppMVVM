package com.akhmedmv.notesappmvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akhmedmv.notesappmvvm.model.Note
import com.akhmedmv.notesappmvvm.utils.TYPE_FIREBASE
import com.akhmedmv.notesappmvvm.utils.TYPE_ROOM

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val readTest: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }

    val dbType: MutableLiveData<String> by lazy {
        MutableLiveData<String>(TYPE_ROOM)
    }

    init {
        readTest.value =
            when (dbType.value) {
                TYPE_ROOM -> {
                    listOf<Note>(
                        Note(title = "Note 1", subtitle = "Subtitle 1"),
                        Note(title = "Note 1", subtitle = "Subtitle 1"),
                        Note(title = "Note 1", subtitle = "Subtitle 1"),
                        Note(title = "Note 1", subtitle = "Subtitle 1"),
                    )
                }

                TYPE_FIREBASE -> {
                    listOf(Note(title = "Note 1", subtitle = "Subtitle 1"))
                }

                else -> {}
            } as List<Note>?
    }

    fun initDataBase(type: String) {
        dbType.value = type
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