package com.example.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val email = intent.getStringExtra("email")


        val liste = findViewById<ListView>(R.id.listePoste)

        val postearray = listOf("Poste 1", "Poste 2", "Poste 3", "Poste 4", "Poste 5")


        val adapt = ArrayAdapter(this, android.R.layout.simple_list_item_1, postearray)
        liste.adapter = adapt




    }
}