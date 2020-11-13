package com.example.note_app.splash_screen

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.note_app.R
import com.example.note_app.create_note.CreateNoteActivity
import com.example.note_app.home.HomeActivity
import kotlinx.coroutines.delay

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreenActivity : AppCompatActivity() {


    private var isFullscreen: Boolean = false


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        isFullscreen = true
        delay(1)


    }






//    fun delayTimer(time:Int){
//        delay(time)
//    }

    private fun delay(dealy:Int){
        val timer = object : Thread() {
            override fun run() {
                try {
                    sleep((dealy * 1000).toLong())
                } catch (e: Exception) {

                } finally {
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        timer.start()
    }
}