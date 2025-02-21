package com.example.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val connect = findViewById<Button>(R.id.button)
        val email = findViewById<EditText>(R.id.text1)
        val password = findViewById<EditText>(R.id.text2)
        val error = findViewById<TextView>(R.id.erreur)

        connect.setOnClickListener {
            error.visibility = View.GONE
            val testEmail = email.text.toString()
            val testPassword = password.text.toString()
            if(testEmail.trim().isEmpty() || testPassword.trim().isEmpty()){
               error.text = "Veuillez remplir tous les champs"
                error.visibility = View.VISIBLE
            }else{

                val emailco= "Nestor@gmail.com"
                val passwordco= "123456"
                if(testEmail == emailco && testPassword == passwordco){
                    email.setText("")
                    password.setText("")
                    Intent(this, HomeActivity::class.java).also {
                        it.putExtra("email", testEmail)
                        startActivity(it)
                    }

                }else {
                    error.text = "Email ou le password n'est pas coorecte "
                    error.visibility = View.VISIBLE
                }
            }

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.hello)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

    }
}