package com.kush.notesmvvm.Models

import android.app.Application
import android.graphics.ColorSpace.Model
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kush.notesmvvm.Database.NoteDatabase
import com.kush.notesmvvm.Database.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotesRepository

    val allnotes: LiveData<List<Note>>
    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository =NotesRepository(dao)
        allnotes= repository.allNotes


    }
    fun deleteNote(note: Note) = viewModelScope.launch { Dispatchers.IO
    repository.Delete(note)}
    fun insertNote(note: Note) = viewModelScope.launch { Dispatchers.IO
    repository.Insert(note)}
    fun UpdateNote(note: Note) = viewModelScope.launch { Dispatchers.IO
        repository.Update(note)}
}
