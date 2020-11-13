package com.example.note_app.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.note_app.database.AppDatabase
import com.example.note_app.database.RoomDAO

class HomeViewModel : ViewModel() {

    val noteList = MutableLiveData<MutableList<NoteModel>>()

    val isDeletedSuccessfully=MutableLiveData<Boolean>()

    fun getNoteList(appDatabase: AppDatabase) {
        val roomDAO: RoomDAO = appDatabase.roomDAO

        noteList.postValue(roomDAO.getAll())
//        Log.d("viewModel",noteList.toString())
    }

    fun deleteItem(appDatabase: AppDatabase,noteModel: NoteModel){
        Log.d("viewModel",noteModel.title)
        val roomDAO: RoomDAO = appDatabase.roomDAO
        roomDAO.delete(noteModel)
        isDeletedSuccessfully.postValue(true)
    }
}