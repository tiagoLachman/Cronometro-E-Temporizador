package com.example.stopwatchandtimer
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val inputEmail = findViewById<EditText>(R.id.inputEmail)
        val inputPassword = findViewById<EditText>(R.id.inputPassword)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog)

        val textView = dialog.findViewById<TextView>(R.id.dialog_textView)




        btnLogin.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()


            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val timer = Intent(this@Login, Timer::class.java)
                        startActivity(timer)
                    } else {
                        //Toast.makeText(baseContext, "Falha no login.", Toast.LENGTH_SHORT).show()
                        textView.text = task.exception?.message
                        dialog.show()
                    }
                }
        }

        btnRegistrar.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val timer = Intent(this@Login, Timer::class.java)
                        startActivity(timer)
                    } else {
                        //Toast.makeText(baseContext, "Falha no registro.", Toast.LENGTH_SHORT).show()
                        textView.text = task.exception?.message
                        dialog.show()
                    }
                }
        }
    }
}