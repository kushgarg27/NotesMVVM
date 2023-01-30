package com.kush.notesmvvm.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes_table")

data class Note(

    @PrimaryKey(autoGenerate = true) val Id : Int?,
    @ColumnInfo(name = "Title") val Title : String?,
    @ColumnInfo(name = "Note")val Note : String?,
    @ColumnInfo(name = "Date")val Date: String?
)
