package com.kush.notesmvvm.Database

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kush.notesmvvm.Models.Note
import java.security.AccessControlContext

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)


abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
    companion object{
        @Volatile
        private var INSTANCE : NoteDatabase? = null
        fun getDatabase(context: Context): NoteDatabase{
            return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,"Note_database").build()
            INSTANCE = instance
            instance

        }
        }
    }
}