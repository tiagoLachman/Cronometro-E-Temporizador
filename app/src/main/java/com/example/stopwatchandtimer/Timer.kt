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
        // Faz essa verificação para caso o botão de resetar seja clicado sem antes ter iniciado um temporizador, lançaria exception
        if (::countdownTimer.isInitialized) {
            countdownTimer.cancel()
        }

        //Seta o valor para 00:00:00 (valor em string.xml)
        viewTempoTimer.text = getString(R.string.txt0)
    }

    //Método para iniciar o temporizador
    //Recebe como argumento o input que o usuario digitou os segundos
    private fun iniciarTemporizador(editText: EditText) {

        //Antes de iniciar, Reseta o temporizador para garantir que não tenha outro rodando ao mesmo tempo
        resetarTemporizador()

        // Guarda em uma variavbel o valor digitado pelo usuario (em segundos)
        val segundosDigitados = editText.text.toString().toIntOrNull() ?: 0

        // Instanciando uma subclasse de CountDownTimer
        // Recebe como argumento os segundos que o usuario digitou * 1000 (para transformar em ms)
        // O outro argumento é quantos ms ele vai esperar para executar a função onTick de novo (1000ms = 1s)
        countdownTimer = object : CountDownTimer((segundosDigitados * 1000).toLong(), 1000) {

            //Esse método é uma implementação da classe CountDownTimer
            // Recebe como argumento os segundosDigitados*1000 que a subclasse anonima acima recebeu
            override fun onTick(tempoRestanteEmMs: Long) {

                //Calcula quantos segundos estão faltando no temporizador
                val segundosFaltando = (tempoRestanteEmMs / 1000).toInt()

                //Seta o texto do temporizador com a quantidade de segundos que falta
                viewTempoTimer.text = segundosFaltando.toString()
            }

            //Esse método é uma implementação da classe CountDownTimer
            // Ao terminar o temporizador, ele é executado e muda o texto para "Completo"
            override fun onFinish() {
                viewTempoTimer.text = getString(R.string.txtComplete)
            }
        }

        // metodo start é implementado dentro da classe CountDownTimer
        countdownTimer.start()
    }

}
