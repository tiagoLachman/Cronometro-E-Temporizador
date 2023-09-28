package com.example.stopwatchandtimer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StopWatch : AppCompatActivity() {
    private lateinit var chronometer: TextView
    private lateinit var startButton: Button
    private lateinit var resetButton: Button
    private var isRunning = false
    private var elapsedTime: Long = 0L
    private var handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch)

        val btnGoToTimer = findViewById<Button>(R.id.btnGoToTimer);
        btnGoToTimer.setOnClickListener {
            val timer = Intent(this@StopWatch, Timer::class.java)
            startActivity(timer)
        }

        chronometer = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.btnStart)
        resetButton = findViewById(R.id.btnReset)

        startButton.setOnClickListener(View.OnClickListener {
            if (!isRunning) {
                startChronometer()
                startButton.text = "Pausar"
            } else {
                pauseChronometer()
                startButton.text = "Continuar"
            }
        })

        resetButton.setOnClickListener(View.OnClickListener {
            resetChronometer()
            startButton.text = "Iniciar"
        })
    }

    private fun startChronometer() {
        handler.postDelayed(updateTimerThread, 0)
        isRunning = true
    }

    private fun pauseChronometer() {
        handler.removeCallbacks(updateTimerThread)
        isRunning = false
    }

    private fun resetChronometer() {
        handler.removeCallbacks(updateTimerThread)
        isRunning = false
        elapsedTime = 0L
        chronometer.text = "00:00:00"
    }


    private val updateTimerThread: Runnable = object : Runnable {
        override fun run() {
            elapsedTime += 1000
            val hours = (elapsedTime / 3600000).toInt()
            val minutes = ((elapsedTime - hours * 3600000) / 60000).toInt()
            val seconds = ((elapsedTime - hours * 3600000 - minutes * 60000) / 1000).toInt()
            chronometer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            handler.postDelayed(this, 1)
            if(hours>99) elapsedTime=0;
        }
    }
}

