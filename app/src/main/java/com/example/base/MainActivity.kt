package com.example.base

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val connect = findViewById<Button>(R.id.button)
        val email = findViewById<EditText>(R.id.text1)
        val password = findViewById<EditText>(R.id.text2)

        connect.setOnClickListener {
            val testEmail = email.text.toString()
            val testPassword = password.text.toString()
            if(testEmail.trim().isEmpty() || testPassword.trim().isEmpty()){
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }else{

                val emailco= "Nestor@gmail.com"
                val passwordco= "123456"
                if(testEmail == emailco && testPassword == passwordco){
                    Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show()
                }else
                    Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
            }

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

    }
}