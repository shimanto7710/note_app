package com.example.note_app.create_note

import android.app.*
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.note_app.R
import com.example.note_app.database.AppDatabase
import com.example.note_app.home.NoteModel
import kotlinx.android.synthetic.main.activity_create_note.*
import java.util.*


class CreateNoteActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dialog: Dialog
    private lateinit var viewModel: CreateNoteViewModel
    private var isNew: Boolean = true
    private var noteId = 0;
    private var noteModel: NoteModel? = null
    private lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        init()
    }


    private fun init() {
        isNew = intent.getBooleanExtra("isNew", true)
        noteId = intent.getIntExtra("id", -1)

        Log.d("createNote", "isNew : $isNew")

        appDatabase = AppDatabase.geAppdatabase(this)
        viewModel = ViewModelProviders.of(this).get(CreateNoteViewModel::class.java)
        if (!isNew) {
            Log.d("createNote", "!!isNew : $isNew")
            viewModel.getNoteById(appDatabase, noteId)
            setNoteListener()

        }

        doneBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.doneBtn -> {
                if (isNew) {
                    addReminder()
                } else {
                    addReminder()
                }
            }
        }
    }

    private fun addReminder() {

        dialog = Dialog(this)
        dialog.setContentView(R.layout.floating_popup)
        val textView: TextView = dialog.findViewById(R.id.date)
        val select: Button
        val add: Button
        select = dialog.findViewById(R.id.selectDate)
        add = dialog.findViewById(R.id.addButton)
        val message: EditText = dialog.findViewById(R.id.message)
        if (!isNew) {
            message.text = Editable.Factory.getInstance().newEditable(noteModel?.title.toString())
            textView.text =
                Editable.Factory.getInstance().newEditable(noteModel?.reminder.toString())
        }

        val newCalender = Calendar.getInstance()
        select.setOnClickListener {
            val dialog = DatePickerDialog(
                this,
                OnDateSetListener { view, year, month, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    val newTime = Calendar.getInstance()
                    val time = TimePickerDialog(
                        this,
                        OnTimeSetListener { view, hourOfDay, minute ->
                            newDate[year, month, dayOfMonth, hourOfDay, minute] = 0
                            val tem =
                                Calendar.getInstance()
                            Log.w(
                                "TIME",
                                System.currentTimeMillis().toString() + ""
                            )
                            if (newDate.timeInMillis - tem.timeInMillis > 0) textView.text =
                                newDate.time.toString() else Toast.makeText(
                                this,
                                "Invalid time",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        newTime[Calendar.HOUR_OF_DAY],
                        newTime[Calendar.MINUTE],
                        true
                    )
                    time.show()
                },
                newCalender[Calendar.YEAR],
                newCalender[Calendar.MONTH],
                newCalender[Calendar.DAY_OF_MONTH]
            )
            dialog.datePicker.minDate = System.currentTimeMillis()

            dialog.show()

        }
        add.setOnClickListener {
            val remind =
                Date(textView.text.toString().trim { it <= ' ' })

            if (isNew) {
                noteModel = NoteModel(
                    Random().nextInt(),
                    message.text.toString().trim(),
                    et_view.text.toString(),
                    Date(),
                    remind
                )
                viewModel.insertNote(appDatabase, noteModel!!)
                viewModel.isInsertSuccessful.observe(this, Observer<Boolean> {
                    if (it) {
                        viewModel.getNoteList(appDatabase)
                        viewModel.noteList.observe(this, Observer<MutableList<NoteModel>> {
                            val l: MutableList<NoteModel>? = it
                            noteModel = l!![l.size - 1]
                            createNotification(noteModel!!)
                        })
                    }
                })
            } else {
                noteModel = NoteModel(
                    noteModel?.id!!,
                    message.text.toString().trim(),
                    et_view.text.toString(),
                    Date(),
                    remind
                )
                noteModel!!.title = message.text.toString().trim()
                noteModel!!.text = et_view.text.toString()
                noteModel!!.reminder = remind

                viewModel.updateNote(appDatabase, noteModel!!)
                viewModel.isUpdateSuccessful.observe(this, Observer<Boolean> {
                    if (it) {
                        createNotification(noteModel!!)
                        Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                })
//            reminders.setRemindDate(remind
            }

//            roomDAO.insert(noteModel)
//            val l: MutableList<NoteModel>? = roomDAO.getAll()
//            noteModel = l!![l.size - 1]


//            setItemsInRecyclerView()
            AppDatabase.destroyInstance()
            dialog.dismiss()
            finish()
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun createNotification(noteModel: NoteModel) {

        val calendar =
            Calendar.getInstance(TimeZone.getTimeZone("GMT+6"))
        calendar.time = noteModel.reminder
        calendar[Calendar.SECOND] = 0
        val intent = Intent(this, NotifierAlarm::class.java)
        intent.putExtra("Title", noteModel.title)
        intent.putExtra("Note", noteModel.text)
        intent.putExtra("RemindDate", noteModel.reminder.toString())
        intent.putExtra("id", noteModel.id)
        val intent1 = PendingIntent.getBroadcast(
            this,
            noteModel.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, intent1)
        Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun setNoteListener() {
        viewModel.note.observe(this, Observer<NoteModel> {
            noteModel = it
            Log.d("createNote", "note " + noteModel!!.title)
            initialize()
        })
    }

    private fun initialize() {
        et_view.text = Editable.Factory.getInstance().newEditable(noteModel?.text.toString())
    }
}