package com.example.note_app.database

import androidx.room.*
import com.example.note_app.home.NoteModel

@Dao
interface RoomDAO {


    //    @Insert
    //    public void Insert(UsernamePassword... usernamePasswords);
    //
    //    @Update
    //    public void Update(UsernamePassword... usernamePasswords);
    //
    //    @Delete
    //    public void Delete(UsernamePassword usernamePassword);
    //
    //    @Query("Select * from login where usename = :username")
    //    public UsernamePassword getUserwithUsername(String username);
    //
    //    @Query("Select * from login where isloggedIn = 1")
    //    public UsernamePassword getLoggedInUser();
    @Insert
    fun insert(vararg noteModels: NoteModel?)

    @Update
    fun update(vararg noteModels: NoteModel?)

    @Delete
    fun delete(noteModel: NoteModel?)

    @Query("SELECT * FROM note where id = :id")
    fun getNoteById(id:Int) : NoteModel

    @Query("Select * from note order by createdAt")
    fun orderThetable(): List<NoteModel?>?

    @Query("Select * from note Limit 1")
    fun getRecentEnteredData(): NoteModel?

    @Query("Select * from note")
    fun getAll(): MutableList<NoteModel>?
}