package com.kush.notesmvvm.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kush.notesmvvm.Models.Note

@Dao

interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note:Note)
    @Delete
    suspend fun delete(note: Note)
    @Query(value = "Select * from Notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>
    @Query("Update notes_table Set title = :title, note = :note where id = :id")
    suspend fun  update(Id: Int? , title : String? , note: String?)
}