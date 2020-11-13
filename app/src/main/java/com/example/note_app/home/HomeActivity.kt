package com.example.note_app.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app.R
import com.example.note_app.create_note.CreateNoteActivity
import com.example.note_app.database.AppDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class HomeActivity : AppCompatActivity(), View.OnClickListener, NoteAdapter.OnItemClickListener,
    NoteAdapter.OnIemLongClickListener, EditPopUp.OnDeleteClickListener,
    EditPopUp.OnEditClickListener {
    private lateinit var appDatabase: AppDatabase
    lateinit var noteAdapter: NoteAdapter
    var itemEditPopUp: EditPopUp? = null
    var noteList = ArrayList<NoteModel>()
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        init()


    }

    private fun init() {
        supportActionBar?.title = "Note"
        appDatabase = AppDatabase.geAppdatabase(this)

        fab.setOnClickListener(this)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getNoteList(appDatabase)
        initRecyclerView()


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.fab -> {
                val intent = Intent(applicationContext, CreateNoteActivity::class.java)
                intent.putExtra("isNew",true)
                startActivity(intent)
            }
        }
    }

    private fun initRecyclerView() {
        postRecyclerView.setHasFixedSize(true)
        postRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext!!.applicationContext)
        postRecyclerView.itemAnimator = DefaultItemAnimator()
        postRecyclerView.layoutManager =
            LinearLayoutManager(postRecyclerView.context, RecyclerView.VERTICAL, false)


        noteAdapter =
            NoteAdapter(noteList)
        postRecyclerView.adapter = noteAdapter

        noteAdapter?.setOnItemClickListener(this)
        noteAdapter?.setOnItemLongClickListener(this)

        setNoteListListener()
    }

    override fun onItemClickListener(noteModel: NoteModel) {
        Log.d("note", "item clicked")
        val intent = Intent(applicationContext, CreateNoteActivity::class.java)
        intent.putExtra("isNew",false)
        intent.putExtra("id",noteModel.id)
        startActivity(intent)

    }

    override fun onItemLongClickListener(noteModel: NoteModel) {
        Log.d("note", "item long clicked")
        Log.d("note", "item long clicked item "+noteModel.title)
        itemEditPopUp = EditPopUp(noteModel)
        itemEditPopUp!!.setItemDeleteListener(this)
        itemEditPopUp!!.setItemEditListener(this)

        itemEditPopUp!!.show(
            this.supportFragmentManager,
            "itemEditPopUp"
        )
    }


    override fun onItemtDeleteClickListener(noteModel: NoteModel?) {
        Log.d("note", "item delete clicked")
        Log.d("note",noteModel?.title!!)
        showDialog(this, noteModel!!)
    }

    override fun onItemEditClickListener(noteModel: NoteModel) {
        Log.d("note", "item edit clicked")
        val intent = Intent(applicationContext, CreateNoteActivity::class.java)
        intent.putExtra("isNew",false)
        intent.putExtra("id",noteModel.id)
        startActivity(intent)
    }

    private fun showDialog(
        activity: Activity?, noteModel: NoteModel
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Are you sure?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialog, id ->
                viewModel.deleteItem(appDatabase, noteModel)
                viewModel.isDeletedSuccessfully.observe(this, Observer<Boolean> {
                    if (it == true) {
                        Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                        noteAdapter.notifyDataSetChanged()
                    }
                })
                noteAdapter.removeItem(noteModel)
//                deleteAPost(post!!)
            }
            .setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }


    override fun onResume() {
        super.onResume()
//        noteList.clear()
//        initRecyclerView()
        viewModel.getNoteList(appDatabase)
        setNoteListListener()
        noteAdapter.notifyDataSetChanged()

    }

    fun setNoteListListener() {
        viewModel.noteList.observe(this,
            Observer<MutableList<NoteModel>> { noteList ->
                this.noteList.clear()
                this.noteList.addAll(noteList)
                noteAdapter.notifyDataSetChanged()
            })
    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
//        a.fl = Intent.FLAG_ACTIVITY_NEW_TASK;
        //        a.fl = Intent.FLAG_ACTIVITY_NEW_TASK;
        a.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(a)
    }

}