package com.example.note_app.create_note

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.note_app.database.AppDatabase
import com.example.note_app.database.RoomDAO
import com.example.note_app.home.NoteModel

class CreateNoteViewModel :ViewModel() {

    val note = MutableLiveData<NoteModel>()
    val isInsertSuccessful = MutableLiveData<Boolean>()
    val noteList = MutableLiveData<MutableList<NoteModel>>()
    val isUpdateSuccessful=MutableLiveData<Boolean>()

    fun getNoteById(appDatabase: AppDatabase,id:Int){
        val roomDAO: RoomDAO = appDatabase.roomDAO
//        Log.d("createNote",roomDAO.getNoteById(id).title)
        note.postValue(roomDAO.getNoteById(id))
    }
    fun insertNote(appDatabase: AppDatabase,noteModel: NoteModel){
        val roomDAO: RoomDAO = appDatabase.roomDAO
        roomDAO.insert(noteModel)
        isInsertSuccessful.postValue(true)
    }

    fun getNoteList(appDatabase: AppDatabase) {
        val roomDAO: RoomDAO = appDatabase.roomDAO

        noteList.postValue(roomDAO.getAll())
//        Log.d("viewModel",noteList.toString())
    }

    fun updateNote(appDatabase: AppDatabase,noteModel: NoteModel){
        val roomDAO: RoomDAO = appDatabase.roomDAO
        roomDAO.update(noteModel)
        isUpdateSuccessful.postValue(true)
    }

}