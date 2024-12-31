package com.akhmedmv.notesappmvvm.database.room.repository

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
        noteRoomDao.addNote(note)
        onSuccess()
    }

    override suspend fun update(
        note: Note,
        onSuccess: () -> Unit
    ) {
        noteRoomDao.updateNote(note)
        onSuccess()
    }

    override suspend fun delete(
        note: Note,
        onSuccess: () -> Unit
    ) {
        noteRoomDao.deleteNote(note)
        onSuccess()
    }
}