package com.example.stopwatchandtimer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Timer : AppCompatActivity() {

    //inicia variaveis
    private lateinit var countdownTimer: CountDownTimer
    private lateinit var viewTempoTimer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //cria variaveis de botao de iniciar, resetar, pra mudar de tela e etc
        viewTempoTimer = findViewById(R.id.viewTempoTimer)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnGoToStopWatch = findViewById<Button>(R.id.btnGoToStopWatch)
        val editText = findViewById<EditText>(R.id.editTextText)

        //Listener para quando clicar no btnStart chamar o método "iniciarTemporizador()" passando como parametro o editText (o input de digitar o valor de segundos)
        btnStart.setOnClickListener { iniciarTemporizador(editText) }

        //Listener para quando clicar no btnReset chamar o método "resetarTemporizador()"
        btnReset.setOnClickListener { resetarTemporizador() }

        //Listener para quando clicar no btnGoToTimer ir para tela de Cronometro
        btnGoToStopWatch.setOnClickListener {
            val stopWatch = Intent(this@Timer, StopWatch::class.java)
            startActivity(stopWatch)
        }


    }
    //método para resetar o temporizador
    private fun resetarTemporizador() {
        // Verifica se countdownTimer foi inicializado, se foi inicializado executa countdownTimer.cancel()
        if (::countdownTimer.isInitialized) {
            countdownTimer.cancel()
        }

        //Seta o valor para 00:00:00 (valor em string.xml)
        viewTempoTimer.text = getString(R.string.txt0)
    }

    private fun iniciarTemporizador(editText: EditText) {
        val segundosDigitados = editText.text.toString().toIntOrNull() ?: 0
        resetarTemporizador()

        countdownTimer = object : CountDownTimer((segundosDigitados * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                viewTempoTimer.text = secondsRemaining.toString()
            }

            override fun onFinish() {
                viewTempoTimer.text = getString(R.string.txtComplete)
            }
        }

        countdownTimer.start()
    }

}
