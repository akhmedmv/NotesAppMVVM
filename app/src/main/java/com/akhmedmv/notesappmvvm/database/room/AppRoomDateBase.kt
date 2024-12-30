package com.akhmedmv.notesappmvvm.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akhmedmv.notesappmvvm.database.room.dao.NoteRoomDao
import com.akhmedmv.notesappmvvm.model.Note

@Database(entities = [Note::class], version = 1)
abstract class AppRoomDateBase : RoomDatabase() {

    abstract fun getRoomDao(): NoteRoomDao

    companion object {

        @Volatile
        private var INSTANCE: AppRoomDateBase? = null

        fun getInstance(context: Context): AppRoomDateBase {
            return if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDateBase::class.java,
                    "notes_database"
                ).build()
                INSTANCE as AppRoomDateBase
            } else INSTANCE as AppRoomDateBase
        }
    }
}