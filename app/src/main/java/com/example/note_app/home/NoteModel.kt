package com.example.note_app.home

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var title :String,
    var text: String,
    var createdAt: Date,
    var reminder:Date
)