package com.akhmedmv.notesappmvvm.database.room.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.akhmedmv.notesappmvvm.database.DataBaseRepository
import com.akhmedmv.notesappmvvm.database.room.dao.NoteRoomDao
import com.akhmedmv.notesappmvvm.model.Note

class RoomRepository(private val noteRoomDao: NoteRoomDao) : DataBaseRepository {
    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override suspend fun create(
        note: Note,
        onSuccess: () -> Unit
    ) {
        try {
            noteRoomDao.addNote(note)
            onSuccess()
        } catch (e: Exception) {
            Log.e("RoomRepository", "Error inserting note", e)
        }
    }

    override suspend fun update(
        note: Note,
        onSuccess: () -> Unit
    ) {
        try {
            noteRoomDao.updateNote(note)
            onSuccess()
        } catch (e: Exception) {
            Log.e("RoomRepository", "Error updating note", e)
        }
    }

    override suspend fun delete(
        note: Note,
        onSuccess: () -> Unit
    ) {
        try {
            noteRoomDao.deleteNote(note)
            onSuccess()
        } catch (e: Exception) {
            Log.d("RoomRepository", "Error deleting note", e)
        }
    }

    override fun signOut() {}
}