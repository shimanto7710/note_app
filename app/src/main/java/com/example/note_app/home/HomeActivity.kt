package com.example.note_app.home

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.note_app.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), View.OnClickListener,NoteAdapter.OnItemClickListener,NoteAdapter.OnIemLongClickListener,EditPopUp.OnDeleteClickListener,EditPopUp.OnEditClickListener {

    lateinit var noteAdapter: NoteAdapter
    var itemEditPopUp: EditPopUp?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        getSupportActionBar()?.title="Note"

        init()


    }

    private fun init() {

        initRecyclerView()
        fab.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.fab -> {
                Snackbar.make(p0, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
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
//        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        itemDecorator.setDrawable(
//            ContextCompat.getDrawable(
//                applicationContext!!,
//                R.drawable.divider
//            )!!
//        )
//        postRecyclerView.addItemDecoration(itemDecorator)

        var list = ArrayList<NoteModel>()
        list.add(NoteModel("asdasdd", Date()))
        list.add(NoteModel("asdasdd", Date()))
        list.add(NoteModel("asdasdd", Date()))

        noteAdapter =
            NoteAdapter(list)
        postRecyclerView.adapter=noteAdapter

        noteAdapter?.setOnItemClickListener(this)
        noteAdapter?.setOnItemLongClickListener(this)
    }

    override fun onItemClickListener(noteModel: NoteModel) {
        Log.d("note","item clicked")
    }

    override fun onItemLongClickListener(noteModel: NoteModel) {
        Log.d("note","item long clicked")
        itemEditPopUp = EditPopUp(noteModel)
        itemEditPopUp!!.setItemDeleteListener(this)
        itemEditPopUp!!.setItemEditListener(this)

        itemEditPopUp!!.show(
            this.supportFragmentManager,
            "itemEditPopUp"
        )
    }



    override fun onItemtDeleteClickListener(noteModel: NoteModel?) {
        Log.d("note","item delete clicked")
        showDialog(this,noteModel!!)
    }

    override fun onItemEditClickListener(noteModel: NoteModel) {
        Log.d("note","item edit clicked")
    }

        private fun showDialog(
            activity: Activity?, noteModel: NoteModel
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Are you sure?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { dialog, id ->noteAdapter.removeItem(noteModel)
//                deleteAPost(post!!)
            }
            .setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

}