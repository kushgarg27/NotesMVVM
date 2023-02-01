package com.kush.notesmvvm.Database


import androidx.lifecycle.LiveData
import com.kush.notesmvvm.Models.Note


class NotesRepository (private val noteDao: NoteDao)
{
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()
    suspend fun Insert(note: Note){
        noteDao.insert(note)
    }
    suspend fun Delete(note: Note){
        noteDao.delete(note)
    }
    suspend fun Update(note: Note){
        noteDao.update(note.Id,note.Title,note.Note)
    }



}
