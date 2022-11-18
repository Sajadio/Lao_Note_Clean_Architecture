package com.sajjadio.laonote.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sajjadio.laonote.data.local.db.dao.NoteDao
import com.sajjadio.laonote.domain.model.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class LaoNoteDB: RoomDatabase() {

    abstract fun notesDao(): NoteDao

}