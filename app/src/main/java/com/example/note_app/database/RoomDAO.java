//package com.example.note_app.database;
//
//import androidx.lifecycle.MutableLiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import com.example.note_app.home.NoteModel;
//
//import java.util.List;
//
//@Dao
//public interface RoomDAO {
//
////    @Insert
////    public void Insert(UsernamePassword... usernamePasswords);
////
////    @Update
////    public void Update(UsernamePassword... usernamePasswords);
////
////    @Delete
////    public void Delete(UsernamePassword usernamePassword);
////
////    @Query("Select * from login where usename = :username")
////    public UsernamePassword getUserwithUsername(String username);
////
////    @Query("Select * from login where isloggedIn = 1")
////    public UsernamePassword getLoggedInUser();
//
//
//    @Insert
//    public void Insert(NoteModel... noteModels);
//
//    @Update
//    public void Update(NoteModel... noteModels);
//
//    @Delete
//    public void Delete(NoteModel reminders);
//
//    @Query("Select * from note order by createdAt")
//    public List<NoteModel> orderThetable();
//
//    @Query("Select * from note Limit 1")
//    public NoteModel getRecentEnteredData();
//
//    @Query("Select * from note")
//    public  M getAll();
//
//}
