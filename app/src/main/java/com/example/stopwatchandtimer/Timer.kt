package com.example.stopwatchandtimer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Timer : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var btnReset: Button
    private lateinit var btnStart: Button
    private lateinit var btnGoToStopWatch: Button
    private lateinit var countdownTimer: CountDownTimer
    private lateinit var viewTempoTimer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer) // Replace with your layout XML file

        editText = findViewById(R.id.editTextText)
        btnReset = findViewById(R.id.btnReset)
        btnStart = findViewById(R.id.btnStart)
        btnGoToStopWatch = findViewById(R.id.btnGoToStopWatch)
        viewTempoTimer = findViewById(R.id.viewTempoTimer);

        btnReset.setOnClickListener { resetTimer() }
        btnStart.setOnClickListener { startTimer() }
        btnGoToStopWatch.setOnClickListener { navigateToStopWatch() }
    }

    private fun resetTimer() {
        // Stop the countdown timer if it's running
        if (::countdownTimer.isInitialized) {
            countdownTimer.cancel()
        }
        // Reset progress bar and EditText
        viewTempoTimer.setText(getString(R.string.txt0))
    }

    private fun startTimer() {
        val initialSeconds = editText.text.toString().toIntOrNull() ?: 0
        val maxSeconds = 10 // Change this to your desired maximum seconds
        resetTimer() // Reset the timer before starting a new one

        countdownTimer = object : CountDownTimer((initialSeconds * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                viewTempoTimer.setText(secondsRemaining.toString())
            }

            override fun onFinish() {
                // Handle timer completion here
                viewTempoTimer.setText("Completo")
            }
        }

        countdownTimer.start()
    }

    private fun navigateToStopWatch() {
        val timer = Intent(this@Timer, StopWatch::class.java)
        startActivity(timer)
    }
}
