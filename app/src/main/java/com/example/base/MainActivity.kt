package com.example.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.base.db.facebookdatabase


class MainActivity : AppCompatActivity() {
    lateinit var sharerepref: SharedPreferences
    lateinit var db : facebookdatabase
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        sharerepref = getSharedPreferences("app_state", MODE_PRIVATE)
        db = facebookdatabase(this, null)

        val authentification = sharerepref.getBoolean("authentification", false)
        val emailShared = sharerepref.getString("email", "")
        if (authentification) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("email", emailShared)
            startActivity(intent)
        }

        val connect = findViewById<Button>(R.id.button)
        val email = findViewById<EditText>(R.id.text1)
        val password = findViewById<EditText>(R.id.text2)
        val error = findViewById<TextView>(R.id.erreur)
        val compte = findViewById<TextView>(R.id.compte)


        compte.setOnClickListener {
            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
        }

        connect.setOnClickListener {
            error.visibility = View.GONE
            val testEmail = email.text.toString()
            val testPassword = password.text.toString()
            if (testEmail.trim().isEmpty() || testPassword.trim().isEmpty()) {
                error.text = "Veuillez remplir tous les champs"
                error.visibility = View.VISIBLE
            } else {
                var user = db.finduser(testEmail, testPassword)
                if (user!=null) {
                    email.setText("")
                    password.setText("")
                    //  Enregistrement dans SharedPreferences
                    val editor = sharerepref.edit()
                    editor.putBoolean("authentification", true)
                    editor.putString("email", testEmail)
                    editor.apply()

                    Log.d("DEBUG", "Avant le lancement de HomeActivity")
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("email", testEmail)
                    startActivity(intent)
                    Log.d("DEBUG", "Après le lancement de HomeActivity")
                } else {
                    error.text = "Email ou le mot de passe n'est pas correct"
                    error.visibility = View.VISIBLE
                }
            }
        }
    }

}