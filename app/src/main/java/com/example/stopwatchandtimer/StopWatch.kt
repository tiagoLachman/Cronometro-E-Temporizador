package com.example.stopwatchandtimer

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StopWatch : AppCompatActivity() {


    // Inicia as variaveis com valor Null
    private var txtCronometro: TextView? = null
    private var btnStart: Button? = null
    private var btnReset: Button? = null
    private var isRunning = false
    private var tempoCorrido: Long = 0L
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch)

        //seta valor na variavel btnGoToTimer
        val btnGoToTimer = findViewById<Button>(R.id.btnGoToTimer)

        //Listener para quando clicar no btnGoToTimer ir para tela de Temporizador
        btnGoToTimer.setOnClickListener {
            val timer = Intent(this@StopWatch, Timer::class.java)
            startActivity(timer)
        }

        //cria variaveis de botao de iniciar, resetar, pra mudar de tela, textos e etc
        txtCronometro = findViewById(R.id.timerTextView)
        btnStart = findViewById(R.id.btnStart)
        btnReset = findViewById(R.id.btnReset)

        //Listener para btn Start
        // Se não tiver rodando, começa a rodar e muda o texto do botão para "pausar"
        // Se Já estiver rodando, pausa o cronometro e muda o texto do botão para "continuar"
        btnStart?.setOnClickListener(View.OnClickListener {
            if (!isRunning) {
                iniciarCronometro()
                btnStart?.text = "Pausar"
            } else {
                pausarCronometro()
                btnStart?.text = "Continuar"
            }
        })

        //Listener para btnReset
        // Zera o cornometro e muda o texto do botão para "iniciar"
        btnReset?.setOnClickListener(View.OnClickListener {
            resetarCronometro()
            btnStart?.text = "Iniciar"
        })
    }

    // Função para INICIAR o cronometro
    // Usa o handler.postDelayed para chamar a função "atualizarTempoCronometro" em "0.5ms" após acionar o botão
    private fun iniciarCronometro() {
        handler.postDelayed(atualizarTempoCronometro, 500)
        isRunning = true
    }

    //Função para PAUSAR o Cronometro
    //Remove tudo que estava na fila para executar /executando na função  "atualizarTempoCronometro"
    // Define "isRunning" como false
    private fun pausarCronometro() {
        handler.removeCallbacks(atualizarTempoCronometro)
        isRunning = false
    }

    //Função para RESETAR o Cronometro
    //Remove tudo que estava na fila para executar /executando na função  "atualizarTempoCronometro"
    // Zera todas as variaveis
    // Define "isRunning" como false
    private fun resetarCronometro() {
        handler.removeCallbacks(atualizarTempoCronometro)
        isRunning = false
        tempoCorrido = 0L
        txtCronometro?.text = "00:00:00"
    }

    // Cria uma função que é uma instancia de "Runnable" (significa que esse codigo pode ser executado em uma thread)
    private val atualizarTempoCronometro: Runnable = object : Runnable {
        override fun run() {
            // Cada vez que executa acrescenta 1000ms em "tempo corrido" que é 1s
            //TESTAR AUMENTAR ESSE VALOR
            tempoCorrido += 1000

            // Calcula as horas/minutos/segundos corridos dividindo a quantidade de "tempoCorrido"
            // 3600000ms = 1h
            // 60000ms = 1min
            // 1000ms = 1s
            val horas = (tempoCorrido / 3600000).toInt()
            val minutos = ((tempoCorrido - horas * 3600000) / 60000).toInt()
            val segundos = ((tempoCorrido - horas * 3600000 - minutos * 60000) / 1000).toInt()

            // Atualiza o valor da variavel "txtCronometro" com o valor de horas, minutos e segundos calculados  nas linhas de cima
            // Utiliza string.format como uma "mascara" para o valor aparecer formatado no padrão "00:00:00"
            txtCronometro?.text = String.format("%02d:%02d:%02d", horas, minutos, segundos)

            //Define que caso as horas passem de 99.9, o cronometro será zerado
            if(horas>99) tempoCorrido=0;

            //Define que a casa 1ms essa função será chamada novamente
            handler.postDelayed(this, 1)


        }


    }
}
